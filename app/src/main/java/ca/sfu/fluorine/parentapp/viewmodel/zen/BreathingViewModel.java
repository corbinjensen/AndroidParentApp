package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.content.Context;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.function.Consumer;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.store.BreathingStorage;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class BreathingViewModel extends ViewModel {
    // Storage
    private final BreathingStorage storage;

    // Live and observable states
    private long millisSecondsLeft;
    private final MutableLiveData<BreathingState> liveBreathingState;

    // Constants
    public final long TOTAL_DURATION;
    public final long TRANSITION;
    public static final long INTERVAL = 500;
    private CountDownTimer timer;

    @Inject
    public BreathingViewModel(@NonNull @ApplicationContext Context context,
                              @NonNull BreathingStorage storage) {
        super();
        this.storage = storage;
        TOTAL_DURATION = context.getResources().getInteger(R.integer.total_breath_duration);
        TRANSITION = TOTAL_DURATION - context.getResources().getInteger(R.integer.breath_release);
        millisSecondsLeft = TOTAL_DURATION;
        liveBreathingState =
                new MutableLiveData<>(new BreathingState(storage.getBreathCount()));
    }

    public LiveData<BreathingState> getLiveBreathingState() {
        return liveBreathingState;
    }

    public int getBreathCount() {
        return storage.getBreathCount();
    }

    public void setBreathCount(int breathCount) {
        storage.storeBreathCount(breathCount);
        liveBreathingState.setValue(new BreathingState(breathCount));
    }

    public void resetBreathingState() {
        liveBreathingState.setValue(new BreathingState(storage.getBreathCount()));
    }

    // Press the button
    public void startBreathing() {
        millisSecondsLeft = TOTAL_DURATION;
        changeState(BreathingState::beginBreathing);
        timer = new CountDownTimer(TOTAL_DURATION, INTERVAL) {
            @Override
            public void onTick(long millisLeft) {
                millisSecondsLeft = TOTAL_DURATION;
            }

            @Override
            public void onFinish() {
                changeState(state -> state.setButtonPressingTooLong(true));
            }
        };
        timer.start();
    }

    // Release the button
    public void stopBreathing() {
        if (timer != null) {
            timer.cancel();
        }
        changeState(state -> {
            state.setButtonPressingTooLong(false);
            if (millisSecondsLeft <= TRANSITION) state.decrementBreathInOut();
        });
        millisSecondsLeft = TOTAL_DURATION;
    }

    private void changeState(@Nullable Consumer<BreathingState> stateModifier) {
        BreathingState oldState = liveBreathingState.getValue();
        if (oldState == null) {
            liveBreathingState.setValue(new BreathingState(storage.getBreathCount()));
        } else {
            liveBreathingState.setValue(oldState.change(stateModifier));
        }
    }
}
