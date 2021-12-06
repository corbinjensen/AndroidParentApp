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
        binding.breatheAgain.setVisibility(View.VISIBLE);
        binding.breatheAgain.setEnabled(true);

        binding.breathingFinished.setVisibility(View.VISIBLE);
        binding.breathingFinished.setEnabled(true);
    }

    @Override
    public void onExit() {
        // Hide the options "Again" or "Finish"
        binding.breatheAgain.setVisibility(View.INVISIBLE);
        binding.breatheAgain.setEnabled(false);

        binding.breathingFinished.setVisibility(View.INVISIBLE);
        binding.breathingFinished.setEnabled(false);

        // Hide the message showing the remaining breath count
        binding.helpBreatheIn.setVisibility(View.INVISIBLE);
    }
}
