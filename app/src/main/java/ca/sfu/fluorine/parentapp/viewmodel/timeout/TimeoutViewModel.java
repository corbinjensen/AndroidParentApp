package ca.sfu.fluorine.parentapp.viewmodel.timeout;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
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
    private final MutableLiveData<Integer> speed = new MutableLiveData<>(TimeoutStorage.NORMAL_PERCENTAGE);
    private final MediatorLiveData<Long> realMillisLeft = new MediatorLiveData<>();
    private final MediatorLiveData<Long> displayMillisLeft = new MediatorLiveData<>();
    private final MutableLiveData<TimeoutState> timerState
            = new MutableLiveData<>(TimeoutState.PAUSED);

    private CountDownTimer timer;

    @Inject
    public TimeoutViewModel(
            @NonNull TimeoutStorage timeoutStorage,
            @NonNull BackgroundTimeoutService backgroundTimeoutService) {
        storage = timeoutStorage;
        service = backgroundTimeoutService;

        // Initialize variable
        realMillisLeft.setValue(0L);
        displayMillisLeft.setValue(0L);
        realMillisLeft.addSource(speed, newSpeed -> {
            long value = (displayMillisLeft.getValue() == null) ? 0 : displayMillisLeft.getValue();
            realMillisLeft.setValue(value * 100 / newSpeed);
        });
        displayMillisLeft.addSource(realMillisLeft, realMillis -> {
            int currentSpeed = (speed.getValue() == null) ? 0 : speed.getValue();
            displayMillisLeft.setValue(realMillis * currentSpeed / 100);
        });
    }

    // Emits data to the view
    public LiveData<Long> getDisplayMillisLeft() {
        return displayMillisLeft;
    }

    public LiveData<TimeoutState> getTimerState() {
        return timerState;
    }

    public long getDisplayTotalDuration() { return totalDuration; }

    public LiveData<Integer> getSpeed() {
        return speed;
    }

    public void setSpeed(int percentageSpeed) {
        speed.setValue(percentageSpeed);
        long duration = (realMillisLeft.getValue() == null) ? 0 : realMillisLeft.getValue();
        invalidateTimer();
        timer = makeTimer(duration);
        if (timerState.getValue() == TimeoutState.RUNNING) {
            timer.start();
        }
    }

    public void loadTimerFromStorage() {
        if (!storage.hasTimer()) {
            timerState.setValue(TimeoutState.EXPIRED);
            return;
        }
        timerState.setValue(TimeoutState.PAUSED);
        totalDuration = storage.getTotalDuration();
        invalidateTimer();
        timer = makeTimerFromSettings();
        if (storage.isTimerRunning()) {
            timerState.setValue(TimeoutState.RUNNING);
            timer.start();
        }
    }

    // Methods for the views interact with this view model
    public void resumeTimer() {
        if (timer == null) {
            makeTimerFromSettings();
        }
        timer.start();
        timerState.setValue(TimeoutState.RUNNING);
    }

    public void pauseTimer() {
        timerState.setValue(TimeoutState.PAUSED);
        saveTimerToStorage();
        invalidateTimer();
    }

    private CountDownTimer makeTimer(long duration) {
        return new CountDownTimer(duration, INTERVAL) {
            @Override
            public void onTick(long remaining) {
                realMillisLeft.setValue(remaining);
            }

            @Override
            public void onFinish() {
                timerState.setValue(TimeoutState.EXPIRED);
            }
        };
    }

    private void invalidateTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Interact with the timeout preferences
    private CountDownTimer makeTimerFromSettings() {
        long duration = storage.calculateRemainingMillis();
        realMillisLeft.setValue(duration);
        speed.setValue(storage.getSpeedPercentage());
        return makeTimer(duration);
    }

    public void resetTimeout() {
        invalidateTimer();
        totalDuration = 0;
    }

    // Will call this one at fragment `onResume`
    public void saveTimerToStorage() {
        long remaining = (realMillisLeft.getValue() == null) ? 0 : realMillisLeft.getValue();
        long expiredTime = System.currentTimeMillis() + remaining;
        int speedPercentage = (speed.getValue() == null) ? TimeoutStorage.NORMAL_PERCENTAGE : speed.getValue();
        storage.getEditor()
                .putBoolean(
                        TimeoutStorage.IS_RUNNING,
                        timerState.getValue() == TimeoutState.RUNNING)
                .putLong(TimeoutStorage.TOTAL_DURATION, totalDuration)
                .putLong(TimeoutStorage.REMAINING_TIME, remaining)
                .putLong(TimeoutStorage.EXPIRED_TIME, expiredTime)
                .putInt(TimeoutStorage.SPEED_PERCENTAGE, speedPercentage)
                .apply();
    }

    // Interact with background service
    public void registerAlarm() {
        if (timerState.getValue() == TimeoutState.RUNNING) {
            long remaining = (realMillisLeft.getValue() == null) ? 0 : realMillisLeft.getValue();
            long expiredTime = System.currentTimeMillis() + remaining;
            service.setAlarmAt(expiredTime);
        }
    }

    public void removeAlarm() {
        service.removeAlarm();
    }
}
