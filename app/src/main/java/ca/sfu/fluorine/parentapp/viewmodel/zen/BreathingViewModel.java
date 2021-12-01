package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.store.BreathingStorage;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BreathingViewModel {
    // Storage
    private final BreathingStorage storage;

    // Live and observable states
    private final MutableLiveData<Integer> breathCycleCount;
    private long millisSecondsLeft;
    private final LiveData<Boolean> breathingState;
    private final LiveData<Integer> breathsLeft; // whether breath in or out
    private final MutableLiveData<Boolean> buttonStillPressing = new MutableLiveData<>(false);

    // Constants
    public static final long TOTAL_DURATION = 100000;
    public static final long TRANSITION = 7000;
    public static final long INTERVAL = 500;
    private CountDownTimer timer;

    @Inject
    public BreathingViewModel(@NonNull BreathingStorage storage) {
        super();
        this.storage = storage;
        millisSecondsLeft = TOTAL_DURATION;
        breathCycleCount = new MutableLiveData<>(0);
        breathsLeft = Transformations.map(breathCycleCount, cycleCount -> (cycleCount + 1) / 2);
        breathingState = Transformations.map(breathCycleCount, count -> count % 2 == 0);

    }

    public void setBreathCount(int breathCount) {
        storage.storeBreathCount(breathCount);
        breathCycleCount.setValue(breathCount * 2);
    }

    public LiveData<Boolean> getBreathingState() {
        return breathingState;
    }

    public LiveData<Integer> getBreathsLeft() {
        return breathsLeft;
    }

    public MutableLiveData<Boolean> isButtonStillPressing() {
        return buttonStillPressing;
    }

    // Press the button
    public void startBreathing() {
        millisSecondsLeft = TOTAL_DURATION;
        buttonStillPressing.setValue(false);
        timer = new CountDownTimer(TOTAL_DURATION, INTERVAL) {
            @Override
            public void onTick(long millisLeft) {
                millisSecondsLeft = TOTAL_DURATION;
            }

            @Override
            public void onFinish() {
                buttonStillPressing.setValue(true);
            }
        };
        timer.start();
    }

    // Release the button
    public void stopBreathing() {
        buttonStillPressing.setValue(false);
        if (timer != null) {
            timer.cancel();
        }
        if (millisSecondsLeft <= TRANSITION) {
            decrementBreathCycleCount();
        }
        millisSecondsLeft = TOTAL_DURATION;
    }

    private void decrementBreathCycleCount() {
        Integer cycleCount = breathCycleCount.getValue();
        if (cycleCount != null) {
            breathCycleCount.setValue(cycleCount - 1);
        } else {
            breathCycleCount.setValue(0);
        }
    }
}
