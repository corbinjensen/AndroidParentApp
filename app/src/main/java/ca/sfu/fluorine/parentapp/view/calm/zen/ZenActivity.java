package ca.sfu.fluorine.parentapp.view.calm.zen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityZenBinding;
import ca.sfu.fluorine.parentapp.viewmodel.zen.BreathingBeginState;
import ca.sfu.fluorine.parentapp.viewmodel.zen.BreathingState;
import ca.sfu.fluorine.parentapp.viewmodel.zen.BreathingViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Represent the screen where users can taking deep breaths for calming
 */
@AndroidEntryPoint
public class ZenActivity extends AppCompatActivity {
    private ActivityZenBinding binding;
    private BreathingState state;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.breatheButton.setOnTouchListener(onTouch);
        state = new BreathingBeginState(this);
    }

    public void setState(BreathingState state) {
        this.state.onExit();
        this.state = state;
        state.onEnter();
    }

    private final View.OnTouchListener onTouch = (View v, MotionEvent event) -> {
        v.performClick();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            state.onButtonDown();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            state.onButtonUp();
        }
        return false;
    };

    public void onAgainButtonClicked(View v) {
        setState(new BreathingBeginState(this));
    }

    public void onFinishButtonClicked(View v) {
        finish();
    }

    public ActivityZenBinding getBinding() {
        return binding;
    }

}