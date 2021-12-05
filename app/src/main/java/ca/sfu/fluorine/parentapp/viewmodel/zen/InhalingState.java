package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.os.CountDownTimer;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class InhalingState extends BreathingState {
    private final int breathCount;
    private final long TOTAL_DURATION, TRANSITION;
    private CountDownTimer countDownTimer;
    private long millisLeft;

    public InhalingState(ZenActivity activity, int breathCount) {
        super(activity);
        this.breathCount = breathCount;
        TOTAL_DURATION = activity.getResources().getInteger(R.integer.total_breath_duration);
        millisLeft = TOTAL_DURATION;
        TRANSITION = TOTAL_DURATION - activity.getResources().getInteger(R.integer.breath_release);
    }

    @Override
    public void onEnter() {
        activity.getBinding().breatheButton.setText(R.string.in);
        activity.getBinding().breathsLeft.setVisibility(View.VISIBLE);
        activity.getBinding().breathsLeft.setText(
                activity.getResources().getQuantityString(
                        R.plurals.breath_count,
                        breathCount,
                        breathCount)
        );
    }

    @Override
    public void onButtonDown() {
        // TODO: Start animation and sound + hide the breath-in message

        millisLeft = TOTAL_DURATION;
        countDownTimer = new CountDownTimer(TOTAL_DURATION, 500) {
            @Override
            public void onTick(long remaining) {
                millisLeft = remaining;
            }

            @Override
            public void onFinish() {
                // TODO: Button is held too long, show the help text to release the button

                // TODO: Stop animation and sound
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onButtonUp() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (millisLeft <= TRANSITION) {
            // TODO: Button is held long enough, go to the exhale state
        } else {
            // TODO: Button is not held long enough, show the breath-in message + cancel sound and animation
        }
    }
}
