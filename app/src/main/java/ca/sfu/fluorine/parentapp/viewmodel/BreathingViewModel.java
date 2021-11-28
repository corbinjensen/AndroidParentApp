package ca.sfu.fluorine.parentapp.viewmodel;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class BreathingViewModel extends AndroidViewModel {
    // Live and observable states
    private final MutableLiveData<Integer> breathCycleCount;
    private final MutableLiveData<Long> millisSecondsLeft;
    private final LiveData<Boolean> breathingState;
    private final LiveData<Integer> breathsLeft; // whether breath in or out
    private final MutableLiveData<Boolean> buttonStillPressing = new MutableLiveData<>(false);

    // Constants
    public static final long TOTAL_DURATION = 100000;
    public static final long TRANSITION = 7000;
    public static final long INTERVAL = 500;
    private CountDownTimer timer;

    public BreathingViewModel(@NonNull Application application) {
        super(application);
        millisSecondsLeft = new MutableLiveData<>(TOTAL_DURATION);
        breathCycleCount = new MutableLiveData<>(0);
        breathsLeft = Transformations.map(breathCycleCount, cycleCount -> (cycleCount + 1) / 2);
        breathingState = Transformations.map(breathCycleCount, count -> count % 2 == 0);

    }

    public void setBreathCount(int breathCount) {
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
        millisSecondsLeft.setValue(TOTAL_DURATION);
        buttonStillPressing.setValue(false);
        timer = new CountDownTimer(TOTAL_DURATION, INTERVAL) {
            @Override
            public void onTick(long millisLeft) {
                millisSecondsLeft.setValue(millisLeft);
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
        Long millisLeft = millisSecondsLeft.getValue();
        if (millisLeft != null && millisLeft <= TRANSITION) {
            decrementBreathCycleCount();
        }
        millisSecondsLeft.setValue(TOTAL_DURATION);
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
