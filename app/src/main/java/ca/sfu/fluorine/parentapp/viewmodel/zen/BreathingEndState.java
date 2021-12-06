package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.view.View;

import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class BreathingEndState extends BreathingState {
    public BreathingEndState(ZenActivity activity) {
        super(activity);
    }

    @Override
    public void onEnter() {
        // Show the options "Again" or "Finish"
        binding.breathingFinishedOption.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExit() {
        // Hide the options "Again" or "Finish"
        binding.breathingFinishedOption.setVisibility(View.GONE);
    }
}
