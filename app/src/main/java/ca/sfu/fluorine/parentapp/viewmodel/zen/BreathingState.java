package ca.sfu.fluorine.parentapp.viewmodel.zen;

import androidx.annotation.Nullable;

import java.util.function.Consumer;

import ca.sfu.fluorine.parentapp.databinding.ActivityZenBinding;
import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

/**
 * Represent the state for the zen activity
 */
public abstract class BreathingState {
    protected ZenActivity activity;

    public BreathingState(ZenActivity activity) {
        this.activity = activity;
    }

    // Run this method when entering the state
    public void onEnter() {}

    // Run this method when exiting the state
    public void onExit() {}

    public void onButtonDown() { }

    public void onButtonUp() { }
}
