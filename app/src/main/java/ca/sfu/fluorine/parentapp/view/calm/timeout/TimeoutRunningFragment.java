package ca.sfu.fluorine.parentapp.view.calm.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutRunningBinding;
import ca.sfu.fluorine.parentapp.service.TimeoutExpiredNotification;
import ca.sfu.fluorine.parentapp.viewmodel.timeout.TimeoutViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Represents the screen of the timer counting down
 */
@AndroidEntryPoint
public class TimeoutRunningFragment extends Fragment {
    private FragmentTimeoutRunningBinding binding;
    private TimeoutViewModel viewModel;
    private int[] speeds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TimeoutViewModel.class);
        speeds = getResources().getIntArray(R.array.speeds);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.timeout_menu, menu);
        SubMenu subMenu = menu.findItem(R.id.change_timer_speed).getSubMenu();
        // Populate the menu here...
        for (final int speed : speeds) {
            String displayPercentage = getString(R.string.timer_speed_cue, speed);
            // Use the ID as a speed value
            subMenu.add(0, speed, Menu.NONE, displayPercentage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //set speed from the item.
        int speed = item.getItemId();

        // Just a compromised work around
        if (speed != R.id.change_timer_speed) {
            viewModel.setSpeed(speed);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTimeoutRunningBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.removeAlarm();
        TimeoutExpiredNotification.hideNotification(requireContext().getApplicationContext());

        // Start the pulsate animation
        binding.pulsator.start();

        // Set up the timer
        viewModel.loadTimerFromStorage();
        int totalDuration = (int) viewModel.getDisplayTotalDuration();
        binding.progressBarTimer.setMax(totalDuration);


        // UI observes view model
        viewModel.getDisplayMillisLeft().observe(getViewLifecycleOwner(), millisLeft -> {
            long remainingInSeconds = millisLeft / 1000;
            int intMillisLeft = millisLeft.intValue();
            int intMillisProgress = totalDuration - intMillisLeft;
            binding.progressBarTimer.setProgress(intMillisProgress);
            binding.countDownText.setText(getString(
                    R.string.remaining_time,
                    remainingInSeconds / 60,
                    remainingInSeconds % 60));
        });

        viewModel.getSpeed().observe(getViewLifecycleOwner(), speedInPercentage ->
            binding.timerVisualCue.setText(getString(R.string.timer_speed_cue, speedInPercentage))
        );

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
