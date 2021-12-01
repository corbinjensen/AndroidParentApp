package ca.sfu.fluorine.parentapp.viewmodel.zen;

import androidx.annotation.Nullable;

import java.util.function.Consumer;

/**
 * Represent the data object of the the view state
 *
 * The UI will change if the whole states get replace, not its fields
 */
public class BreathingState {
    // Make the breathing state exposed to the interface
    // However, changing the parameters with the instance won't affect the UI
    private boolean breathingBegan = false;
    private int breathingInOut;
    private boolean isButtonPressingTooLong = false;
    private boolean breathingFinished = false;

    private BreathingState() {}

    public BreathingState(int breathCount) {
        breathingInOut = breathCount * 2;
    }

    // Return new copy instance of BreathingState with a few modifications
    BreathingState change(@Nullable Consumer<BreathingState> stateModifier) {
        BreathingState newState = new BreathingState();
        newState.breathingBegan = breathingBegan;
        newState.breathingInOut = breathingInOut;
        newState.isButtonPressingTooLong = isButtonPressingTooLong;
        newState.breathingFinished = breathingFinished;
        if (stateModifier != null) {
            stateModifier.accept(newState);
        }
        return newState;
    }

    public int getRemainingBreathCount() {
        return (breathingInOut + 1) / 2;
    }

    public boolean isInhaling() {
        return breathingInOut % 2 == 0;
    }

    public boolean hasBreathingStarted() {
        return breathingBegan;
    }

    public boolean hasBreathingFinished() {
        return breathingFinished;
    }

    public boolean isButtonPressingTooLong() {
        return isButtonPressingTooLong;
    }

    public void beginBreathing() {
        breathingBegan = true;
    }

    public void setButtonPressingTooLong(boolean buttonPressingTooLong) {
        isButtonPressingTooLong = buttonPressingTooLong;
    }

    public void decrementBreathInOut() {
        if (breathingInOut > 0) breathingInOut--;
        if (breathingInOut <= 0) {
            breathingFinished = true;
            breathingBegan = true;
        }
    }
}
