package ca.sfu.fluorine.parentapp.view.calm.zen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityZenBinding;
import ca.sfu.fluorine.parentapp.viewmodel.zen.BreathingState;
import ca.sfu.fluorine.parentapp.viewmodel.zen.BreathingViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Represent the screen where users can taking deep breaths for calming
 */
@AndroidEntryPoint
public class ZenActivity extends AppCompatActivity {
    private ActivityZenBinding binding;
    private BreathingViewModel viewModel;
    private int breathCount, minBreathCount, maxBreathCount;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // TODO: Begin pulsator at appropriate time (with breathe in/out)
        binding.zenPulsator.start();

        viewModel = new ViewModelProvider(this).get(BreathingViewModel.class);
        minBreathCount = getResources().getInteger(R.integer.min_breath_count);
        maxBreathCount = getResources().getInteger(R.integer.max_breath_count);
        breathCount = viewModel.getBreathCount();
        updateBreathCount();

        viewModel.getLiveBreathingState().observe(this, state -> {
            if (!state.hasBreathingStarted()) {
                showUIBeforeBreathing();
                return;
            }
            if (state.hasBreathingFinished()) {
                showUIAfterBreathing();
                return;
            }
            showUIWhileBreathing(state);
        });
        binding.breatheButton.setOnTouchListener(onTouch);
    }

    public void incrementBreathCount(View v) {
        if (breathCount >= maxBreathCount) {
            breathCount = maxBreathCount;
        } else {
            breathCount++;
        }
        updateBreathCount();
    }

    public void decrementBreathCount(View v) {
        if (breathCount <= minBreathCount) {
            breathCount = minBreathCount;
        } else {
            breathCount--;
        }
        updateBreathCount();
    }

    private void showUIBeforeBreathing() {
        binding.breathCountSelection.setVisibility(View.VISIBLE);
        binding.breathingFinishedOption.setVisibility(View.GONE);
        binding.releaseButton.setVisibility(View.GONE);
        binding.breathsLeft.setVisibility(View.GONE);
        binding.helpBreatheIn.setVisibility(View.GONE);
        binding.breatheButton.setText(R.string.begin);
    }

    private void showUIAfterBreathing() {
        binding.breathCountSelection.setVisibility(View.GONE);
        binding.breathingFinishedOption.setVisibility(View.VISIBLE);
        binding.releaseButton.setVisibility(View.GONE);
        binding.breathsLeft.setVisibility(View.GONE);
        binding.breatheButton.setVisibility(View.VISIBLE);
        binding.breatheButton.setText(R.string.good_job);
    }

    private void showUIWhileBreathing(BreathingState state) {
        binding.breathCountSelection.setVisibility(View.GONE);
        binding.breathingFinishedOption.setVisibility(View.GONE);
        binding.breathsLeft.setVisibility(View.VISIBLE);
        binding.breatheButton.setVisibility(View.VISIBLE);
        binding.breatheButton.setText(state.isInhaling() ? R.string.in : R.string.out);
        binding.releaseButton.setVisibility(state.isButtonPressingTooLong() ? View.VISIBLE : View.GONE);
        int breathLeft = state.getRemainingBreathCount();
        if (breathLeft <= 1) {
            binding.breathsLeft.setText(R.string.last_breath);
        } else {
            binding.breathsLeft.setText(getString(R.string.breath_left, breathLeft));
        }
    }

    private void updateBreathCount() {
        String breathCountDisplay = getResources()
                .getQuantityString(R.plurals.breath_count, breathCount, breathCount);
        binding.breathCount.setText(breathCountDisplay);
        viewModel.setBreathCount(breathCount);
    }

    private final View.OnTouchListener onTouch = (View v, MotionEvent event) -> {
        v.performClick();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            viewModel.startBreathing();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            viewModel.stopBreathing();
        }
        return false;
    };

    public void onAgainButtonClicked(View v) {
        viewModel.resetBreathingState();
    }

    public void onFinishButtonClicked(View v) {
        finish();
    }
}