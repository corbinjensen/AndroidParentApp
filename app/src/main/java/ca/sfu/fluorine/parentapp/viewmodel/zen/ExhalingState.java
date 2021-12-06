package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.media.MediaPlayer;
import android.os.CountDownTimer;

import androidx.annotation.StringRes;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class ExhalingState extends BreathingState {
    private int breathCount;
    private final long TOTAL_DURATION;
    private final long TRANSITION_DURATION;
    private CountDownTimer countDownTimer;
    private long millisLeft;

    public ExhalingState(ZenActivity activity, int breathCount) {
        super(activity);
        this.breathCount = breathCount;
        TOTAL_DURATION = activity.getResources().getInteger(R.integer.total_breath_duration);
        TRANSITION_DURATION = TOTAL_DURATION - 3000;
    }

    @Override
    public void onEnter() {
        // TODO: Finish all tasks below
        // Start animation and sound

        // Disable the button
        binding.breatheButton.setEnabled(false);

        millisLeft = TOTAL_DURATION;
        countDownTimer = new CountDownTimer(TOTAL_DURATION, 500) {
            @Override
            public void onTick(long remaining) {millisLeft = remaining; }

            @Override
            public void onFinish(){
                if(millisLeft <= TRANSITION_DURATION){
                    // After 3 seconds, change the button + update the remaining breath count
                    binding.breatheButton.setText(R.string.out);
                    breathCount--;
                }
                if(millisLeft <= 0){
                    // Stop sound and animation after 10 seconds in total
                }

            }
        };


    }

    @Override
    public void onButtonDown() {
        // TODO: Finish all tasks below
        // Stop sound and animation


        if(breathCount <= 0){
            // If there is no more breaths, switch to finishing state
        }else{
            // Otherwise, switch to the inhaling state
        }

    }
}
