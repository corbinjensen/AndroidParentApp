package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.service.MediaController;
import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class InhalingState extends BreathingState {
    private final int breathCount;
    private final long TOTAL_DURATION, TRANSITION;
    private CountDownTimer countDownTimer;
    private long millisLeft;
    public MediaPlayer inhaleSound;

    public InhalingState(ZenActivity activity, int breathCount) {
        super(activity);
        this.breathCount = breathCount;
        TOTAL_DURATION = activity.getResources().getInteger(R.integer.total_breath_duration);
        millisLeft = TOTAL_DURATION;
        TRANSITION = TOTAL_DURATION - activity.getResources().getInteger(R.integer.breath_release);
        inhaleSound = MediaPlayer.create(activity,R.raw.inhaling);
    }

    @Override
    public void onEnter() {
        binding.breatheButton.setText(R.string.in);
        binding.breathsLeft.setVisibility(View.VISIBLE);
        binding.breathsLeft.setText(
                activity.getResources().getQuantityString(
                        R.plurals.breath_count,
                        breathCount,
                        breathCount)
        );
        binding.helpBreatheIn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onButtonDown() {
        // TODO: Start animation and sound + hide the breath-in message
        // inhaleSound.start();

        // Hide breath-in message
        binding.helpBreatheIn.setVisibility(View.INVISIBLE);


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
                // inhaleSound.stop();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onButtonUp() {

        binding.helpBreatheIn.setVisibility(View.VISIBLE);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (millisLeft <= TRANSITION) {
            // TODO: Button is held long enough, go to the exhale state
        } else {
            // TODO: Button is not held long enough, show the breath-in message + cancel sound and animation
            // inhaleSound.stop();
            binding.helpBreatheIn.setVisibility(View.VISIBLE);
        }
    }
}
