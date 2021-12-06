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
    private boolean doneInhaling;
    public MediaPlayer inhaleSound;

    public InhalingState(ZenActivity activity, int breathCount) {
        super(activity);
        this.breathCount = breathCount;
        TOTAL_DURATION = activity.getResources().getInteger(R.integer.total_breath_duration);
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

        binding.helpBreatheIn.setVisibility(View.INVISIBLE);

        doneInhaling = false;
        countDownTimer = new CountDownTimer(TOTAL_DURATION, 500) {
            @Override
            public void onTick(long remaining) {
                if (remaining <= TRANSITION) {
                    doneInhaling = true;
                    binding.breatheButton.setText(R.string.out);
                }
            }

            @Override
            public void onFinish() {
                binding.releaseButton.setVisibility(View.VISIBLE);
                // TODO: Cancel animation and sound
                // inhaleSound.stop();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onButtonUp() {
        // TODO: Cancel sound and animation

        binding.helpBreatheIn.setVisibility(View.VISIBLE);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (doneInhaling) {
            activity.setState(new ExhalingState(activity, breathCount));
        } else {
            binding.helpBreatheIn.setVisibility(View.VISIBLE);
        }
    }
}
