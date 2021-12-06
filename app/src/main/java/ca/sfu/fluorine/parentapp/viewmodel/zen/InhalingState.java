package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
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
    }

    @Override
    public void onEnter() {
        binding.breatheButton.setText(R.string.in);
        binding.breathsLeft.setVisibility(View.VISIBLE);
        if (breathCount > 1) {
            binding.breathsLeft.setText(activity.getString(R.string.breath_left, breathCount));
        } else {
            binding.breathsLeft.setText(R.string.last_breath);
        }

        binding.helpBreath.setVisibility(View.VISIBLE);
        binding.helpBreath.setText(R.string.help_breathe_in);

        // Change the pulsator color
        binding.inhalePulsator.setVisibility(View.INVISIBLE);
        binding.inhalePulsator.setColor(activity.getColor(R.color.complementary_300));
    }

    @Override
    public void onExit() {
        binding.helpBreath.setVisibility(View.GONE);
        binding.releaseButton.setVisibility(View.GONE);

        // Clean up the media
        stopSound();
        binding.inhalePulsator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onButtonDown() {
        inhaleSound = MediaPlayer.create(activity, R.raw.inhaling);
        inhaleSound.start();
        binding.inhalePulsator.setVisibility(View.VISIBLE);
        binding.inhalePulsator.start();

        binding.helpBreath.setVisibility(View.INVISIBLE);

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

                binding.inhalePulsator.stop();
                binding.inhalePulsator.setVisibility(View.INVISIBLE);
                stopSound();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onButtonUp() {
        binding.inhalePulsator.stop();
        binding.inhalePulsator.setVisibility(View.INVISIBLE);
        stopSound();

        binding.helpBreath.setVisibility(View.VISIBLE);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (doneInhaling) {
            activity.setState(new ExhalingState(activity, breathCount));
        } else {
            binding.helpBreath.setVisibility(View.VISIBLE);
        }
    }

    private void stopSound() {
        if (inhaleSound == null) return;
        inhaleSound.stop();
        inhaleSound.release();
        inhaleSound = null;
    }
}
