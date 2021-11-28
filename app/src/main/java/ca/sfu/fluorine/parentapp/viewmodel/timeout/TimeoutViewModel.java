package ca.sfu.fluorine.parentapp.viewmodel.timeout;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.service.BackgroundTimeoutService;
import ca.sfu.fluorine.parentapp.store.TimeoutStorage;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TimeoutViewModel extends ViewModel {
    // Services and storages
    private final TimeoutStorage storage;
    private final BackgroundTimeoutService service;

    // Constants
    private final long INTERVAL = 100;
    public enum TimeoutState {RUNNING, PAUSED, EXPIRED}

    // Live data
    private long totalDuration = 0;
    private final MutableLiveData<Long> millisLeft = new MutableLiveData<>(0L);
    private final MutableLiveData<TimeoutState> timerState
            = new MutableLiveData<>(TimeoutState.PAUSED);

    private CountDownTimer timer;

    @Inject
    public TimeoutViewModel(
            @NonNull TimeoutStorage timeoutStorage,
            @NonNull BackgroundTimeoutService backgroundTimeoutService) {
        storage = timeoutStorage;
        service = backgroundTimeoutService;
    }

    // Emits data to the view
    public LiveData<Long> getMillisLeft() {
        return millisLeft;
    }

    public LiveData<TimeoutState> getTimerState() {
        return timerState;
    }

    //Gives int to represent percent time elapsed since timer's creation (on scale low:0-high:100)
    public int getProgressInt(){
        long totalMillis = totalDuration / 100 ;
        long currentMillis = 0;
        if (millisLeft.getValue() != null){
            currentMillis = millisLeft.getValue();
        }
        int progressCounter = (int) (currentMillis / totalMillis);
        return (100 - progressCounter);
    }

    // Methods for the views interact with this view model
    public void resumeTimer() {
        makeTimerFromSettings();
        timer.start();
        timerState.setValue(TimeoutState.RUNNING);
    }

    public void pauseTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timerState.setValue(TimeoutState.PAUSED);
        saveTimerToStorage();
    }

    // Interact with the timeout preferences
    private void makeTimerFromSettings() {
        long duration = storage.calculateRemainingMillis();
        millisLeft.setValue(duration);
        timer = new CountDownTimer(duration, INTERVAL) {
            @Override
            public void onTick(long remaining) {
                millisLeft.setValue(remaining);
            }

            @Override
            public void onFinish() {
                timerState.setValue(TimeoutState.EXPIRED);
            }
        };
    }

    public void loadTimerFromStorage() {
        if (!storage.hasTimer()) {
            timerState.setValue(TimeoutState.EXPIRED);
            return;
        }
        timerState.setValue(TimeoutState.PAUSED);
        totalDuration = storage.getTotalDuration();
        makeTimerFromSettings();
        if (storage.isTimerRunning()) {
            timerState.setValue(TimeoutState.RUNNING);
            timer.start();
        }
    }

    public void resetTimeout() {
        pauseTimer();
        totalDuration = 0;
    }

    // Will call this one at fragment `onResume`
    public void saveTimerToStorage() {
        long remaining = (millisLeft.getValue() == null) ? 0 : millisLeft.getValue();
        long expiredTime = System.currentTimeMillis() + remaining;
        storage.getEditor()
                .putBoolean(
                        TimeoutStorage.IS_RUNNING,
                        timerState.getValue() == TimeoutState.RUNNING)
                .putLong(TimeoutStorage.TOTAL_DURATION, totalDuration)
                .putLong(TimeoutStorage.REMAINING_TIME, remaining)
                .putLong(TimeoutStorage.EXPIRED_TIME, expiredTime)
                .apply();
    }

    // Interact with background service
    public void registerAlarm() {
        if (timerState.getValue() == TimeoutState.RUNNING) {
            long remaining = (millisLeft.getValue() == null) ? 0 : millisLeft.getValue();
            long expiredTime = System.currentTimeMillis() + remaining;
            service.setAlarmAt(expiredTime);
        }
    }

    public void removeAlarm() {
        service.removeAlarm();
    }
}
