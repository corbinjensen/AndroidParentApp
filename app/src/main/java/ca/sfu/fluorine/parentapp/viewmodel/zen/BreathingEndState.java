package ca.sfu.fluorine.parentapp.viewmodel.zen;

import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class BreathingEndState extends BreathingState {
    public BreathingEndState(ZenActivity activity) {
        super(activity);
    }

    @Override
    public void onEnter() {
        // TODO: Show the options "Again" or "Finish"
    }

    @Override
    public void onExit() {
        // TODO: Hide the options "Again" or "Finish"

        // TODO: Hide the message showing the remaining breath count
    }
}
