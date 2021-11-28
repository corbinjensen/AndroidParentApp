package ca.sfu.fluorine.parentapp.view.calm.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutRunningBinding;
import ca.sfu.fluorine.parentapp.service.TimeoutExpiredNotification;
import ca.sfu.fluorine.parentapp.view.utils.NoActionBarFragment;
import ca.sfu.fluorine.parentapp.viewmodel.timeout.TimeoutViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Represents the screen of the timer counting down
 */
@AndroidEntryPoint
public class TimeoutRunningFragment extends NoActionBarFragment {
    private FragmentTimeoutRunningBinding binding;
    private TimeoutViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTimeoutRunningBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TimeoutViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.removeAlarm();
        TimeoutExpiredNotification.hideNotification(requireContext().getApplicationContext());

        // Start the pulsator
        binding.pulsator.start();

        // Set up the timer
        viewModel.loadTimerFromStorage();
        int totalDuration = (int)viewModel.getTotalDuration();
        binding.progressBarTimer.setMax(totalDuration);


        // UI observes view model
        viewModel.getMillisLeft().observe(getViewLifecycleOwner(), millisLeft -> {
            long remainingInSeconds = millisLeft / 1000;
            int intMillisLeft = (millisLeft != null) ? millisLeft.intValue() : 0;
            int intMillisProgress = totalDuration - intMillisLeft;
            binding.progressBarTimer.setProgress(intMillisProgress);
            binding.countDownText.setText(getString(
                    R.string.remaining_time,
                    remainingInSeconds / 60,
                    remainingInSeconds % 60));
        });

        viewModel.getTimerState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case RUNNING:
                    binding.playButton.setText(R.string.pause);
                    binding.playButton.setOnClickListener(v -> viewModel.pauseTimer());
                    break;
                case PAUSED:
                    binding.playButton.setText(R.string.resume);
                    binding.playButton.setOnClickListener(v -> viewModel.resumeTimer());
                    break;
                case EXPIRED:
                    viewModel.resetTimeout();
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.redirect_to_end_screen);
                    break;
            }
        });

        binding.resetButton.setOnClickListener((btnView) -> {
            NavHostFragment.findNavController(this).navigate(R.id.reset_timer_action);
            viewModel.resetTimeout();
        });
    }

    // Discard the timer when the fragment is no longer visible
    @Override
    public void onStop() {
        viewModel.saveTimerToStorage();
        viewModel.registerAlarm();

        super.onStop();
    }
}
