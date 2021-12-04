package ca.sfu.fluorine.parentapp.viewmodel.zen;

import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class BreathingEndState extends BreathingState {
    public BreathingEndState(ZenActivity activity) {
        super(activity);
    }

    @Override
    public void onEnter() {
        // Show the options "Again" or "Finish"
    }

    @Override
    public void onExit() {
        // Hide the options "Again" or "Finish"

        // Hide the breath left
    }
}
