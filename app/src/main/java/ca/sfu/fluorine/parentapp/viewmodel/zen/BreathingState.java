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

    // Return new instance of BreathingState with a few modifications
    public BreathingState change(@NonNull BreathingState oldState,
                               @Nullable Consumer<BreathingState> stateModifier) {
        BreathingState newState = new BreathingState();
        newState.isBreathingBegin = oldState.isBreathingBegin;
        newState.breathingInOut = oldState.breathingInOut;
        newState.isButtonPressingTooLong = oldState.isButtonPressingTooLong;
        newState.isBreathingFinish = oldState.isBreathingFinish;
        if (stateModifier != null) {
            stateModifier.accept(newState);
        }
        return newState;
    }
}
