package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class ExhalingState extends BreathingState {
    private int breathCount;
    private final long TOTAL_DURATION;
    private final long TRANSITION_DURATION;
    private CountDownTimer countDownTimer;
    private boolean doneExhaling = false;
    private MediaPlayer exhaleSound;

    public ExhalingState(ZenActivity activity, int breathCount) {
        super(activity);
        this.breathCount = breathCount;
        TOTAL_DURATION = activity.getResources().getInteger(R.integer.total_breath_duration);
        TRANSITION_DURATION = TOTAL_DURATION - 3000;
        exhaleSound = MediaPlayer.create(activity, R.raw.exhaling);
    }

    @Override
    public void onEnter() {
        binding.exhalePulsator.setVisibility(View.VISIBLE);
        binding.exhalePulsator.start();
        exhaleSound.start();

        binding.breatheButton.setEnabled(false);
        binding.helpBreath.setVisibility(View.VISIBLE);
        binding.helpBreath.setText(R.string.help_breathe_out);

        countDownTimer = new CountDownTimer(TOTAL_DURATION, 500) {
            @Override
            public void onTick(long remaining) {
                if (remaining <= TRANSITION_DURATION) {
                    // change the button + update the remaining breath count
                    binding.breatheButton.setEnabled(true);
                    if (!doneExhaling) {
                        breathCount--;
                        if (breathCount <= 0) {
                            binding.breatheButton.setText(R.string.good_job);
                            binding.breathsLeft.setVisibility(View.GONE);
                        } else {
                            binding.breatheButton.setText(R.string.in);
                            if (breathCount == 1) {
                                binding.breathsLeft.setText(R.string.last_breath);
                            } else {
                                binding.breathsLeft.setText(
                                        activity.getString(R.string.breath_left, breathCount));
                            }
                        }
                        doneExhaling = true;
                    }
                }
            }

            @Override
            public void onFinish(){
                moveToNextState();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onExit() {
        binding.exhalePulsator.setVisibility(View.INVISIBLE);
        binding.helpBreath.setVisibility(View.GONE);
        exhaleSound.stop();
        exhaleSound.release();
        exhaleSound = null;
    }

    @Override
    public void onButtonDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        moveToNextState();
    }

    private void moveToNextState() {
        if (breathCount <= 0) {
            activity.setState(new BreathingEndState(activity));
        } else {
            activity.setState(new InhalingState(activity, breathCount));
        }
    }
}
