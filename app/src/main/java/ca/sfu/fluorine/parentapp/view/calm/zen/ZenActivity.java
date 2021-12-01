package ca.sfu.fluorine.parentapp.view.calm.zen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityZenBinding;
import ca.sfu.fluorine.parentapp.viewmodel.zen.BreathingViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ZenActivity extends AppCompatActivity {
    private ActivityZenBinding binding;
    private BreathingViewModel viewModel;
    private int breathCount, minBreathCount, maxBreathCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(BreathingViewModel.class);
        minBreathCount = getResources().getInteger(R.integer.min_breath_count);
        maxBreathCount = getResources().getInteger(R.integer.max_breath_count);
        breathCount = viewModel.getBreathCount();

        viewModel.getLiveBreathingState().observe(this, state -> {
            if (!state.hasBreathingStarted()) {
                showUIOnStartBreathing();
                return;
            }
            if (state.hasBreathingFinished()) {
                showUIOnFinishBreathing();
                return;
            }
            showUIWhileBreathing();
        });
    }

    public void incrementBreathCount(View v) {
        if (breathCount >= maxBreathCount) {
            viewModel.setBreathCount(maxBreathCount);
        } else {
            viewModel.setBreathCount(++breathCount);
        }
    }

    public void decrementBreathCount(View v) {
        if (breathCount <= maxBreathCount) {
            viewModel.setBreathCount(minBreathCount);
        } else {
            viewModel.setBreathCount(--breathCount);
        }
    }

    private void showUIOnStartBreathing() {

    }

    private void showUIOnFinishBreathing() {

    }

    private void showUIWhileBreathing() {

    }
}