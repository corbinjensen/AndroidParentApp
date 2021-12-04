package ca.sfu.fluorine.parentapp.viewmodel.zen;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class ExhalingState extends BreathingState {
    private int breathCount;
    private final long TOTAL_DURATION;

    public ExhalingState(ZenActivity activity, int breathCount) {
        super(activity);
        this.breathCount = breathCount;
        TOTAL_DURATION = activity.getResources().getInteger(R.integer.total_breath_duration);
    }

    @Override
    public void onEnter() {
        // TODO: Finish all tasks below
        // Start animation and sound

        // Disable the button

        // After 3 seconds, change the button + update the remaining breath count

        // Stop sound and animation after 10 seconds in total
    }

    @Override
    public void onButtonDown() {
        // TODO: Finish all tasks below
        // Stop sound and animation

        // If there is no more breaths, switch to finishing state

        // Otherwise, switch to the inhaling state
    }
}
