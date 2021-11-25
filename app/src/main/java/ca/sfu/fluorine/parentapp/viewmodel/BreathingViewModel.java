package ca.sfu.fluorine.parentapp.viewmodel;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BreathingViewModel extends AndroidViewModel {
    private boolean is_inhaling = true;
    private final MutableLiveData<Integer> liveBreathCount;
    private final MutableLiveData<Boolean> liveBreathState;
    private final MutableLiveData<Long> millisSecondsLeft;

    // Timer marker
    public static final long TOTAL_DURATION = 100000;
    public static final long TRANSITION = 7000;
    public static final long INTERVAL = 500;
    private CountDownTimer timer;

    public BreathingViewModel(@NonNull Application application) {
        super(application);
        liveBreathCount = new MutableLiveData<>(0);
        liveBreathState = new MutableLiveData<>(is_inhaling);
        millisSecondsLeft = new MutableLiveData<>(TOTAL_DURATION);
    }

    public void setBreathCount(int breathCount) {
        liveBreathCount.postValue(breathCount);
    }

    public MutableLiveData<Integer> getLiveBreathCount() {
        return liveBreathCount;
    }

    public MutableLiveData<Boolean> getLiveBreathState() {
        return liveBreathState;
    }

    public MutableLiveData<Long> getMillisSecondsLeft() {
        return millisSecondsLeft;
    }

    public void startBreathing() {
        millisSecondsLeft.postValue(TOTAL_DURATION);
        timer = new CountDownTimer(TOTAL_DURATION, INTERVAL) {
            @Override
            public void onTick(long millisLeft) {
                millisSecondsLeft.postValue(millisLeft);
            }

            @Override
            public void onFinish() {
                // Switch the state
                switchState(true);
            }
        };
    }

    public void stopBreathing(boolean isDone) {
        if (timer != null) {
            timer.cancel();
        }
        if (isDone) {
            switchState(isDone);
        }
    }

    private void switchState(boolean isDone) {
        if (isDone) {
            is_inhaling = !is_inhaling;
            liveBreathState.postValue(is_inhaling);
        }
    }
}
