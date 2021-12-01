package ca.sfu.fluorine.parentapp.viewmodel.zen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.function.Consumer;

/**
 * Represent the data object of the the view state
 *
 * The UI will change if new state replace the old state
 */
public class BreathingState {
    // Make the breathing state exposed to the interface
    // However, changing the parameters with the instance won't affect the UI
    boolean isBreathingBegin = false;
    int breathingInOut;
    boolean isButtonPressingTooLong = false;
    boolean isBreathingFinish = false;

    private BreathingState() {}

    public BreathingState(int breathCount) {
        breathingInOut = breathCount * 2;
    }

    // Return new copy instance of BreathingState with a few modifications
    public BreathingState change(@Nullable Consumer<BreathingState> stateModifier) {
        BreathingState newState = new BreathingState();
        newState.isBreathingBegin = isBreathingBegin;
        newState.breathingInOut = breathingInOut;
        newState.isButtonPressingTooLong = isButtonPressingTooLong;
        newState.isBreathingFinish = isBreathingFinish;
        if (stateModifier != null) {
            stateModifier.accept(newState);
        }
        return newState;
    }

    public int getRemainingBreathCount() {
        return (breathingInOut + 1) % 2;
    }

    public boolean isInhaling() {
        return breathingInOut % 2 == 0;
    }
}
