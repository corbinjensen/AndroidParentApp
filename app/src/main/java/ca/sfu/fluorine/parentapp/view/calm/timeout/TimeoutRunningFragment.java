package ca.sfu.fluorine.parentapp.view.calm.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

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

    public void showTimerSpeedMenu(View v) {
        PopupMenu timerSpeedMenu = new PopupMenu(getContext(), v);
        MenuInflater inflater = timerSpeedMenu.getMenuInflater();
        inflater.inflate(R.menu.timeout_time_select_menu, timerSpeedMenu.getMenu());
        timerSpeedMenu.setOnMenuItemClickListener(menuItem -> onMenuItemSelect(menuItem));
        timerSpeedMenu.show();
    }

    public boolean onMenuItemSelect(MenuItem item) {
        Integer speed;
        int setSpeed25P = 25;
        int setSpeed50P = 50;
        int setSpeed75P = 75;
        int setSpeed100P = 100;
        int setSpeed200P = 200;
        int setSpeed300P = 300;
        int setSpeed400P = 400;

        switch (item.getItemId()) {
            case R.id.timer_speed_0_25x:
               speed = viewModel.getSpeed().getValue();
                assert speed != null;
                if(!speed.equals(setSpeed25P)){
                    viewModel.setSpeed(setSpeed25P);
                }
                break;
            case R.id.timer_speed_0_50x:
                speed = viewModel.getSpeed().getValue();
                assert speed != null;
                if(!speed.equals(setSpeed50P)){
                    viewModel.setSpeed(setSpeed50P);
                }
                break;
            case R.id.timer_speed_0_75x:
                speed = viewModel.getSpeed().getValue();
                assert speed != null;
                if(!speed.equals(setSpeed75P)){
                    viewModel.setSpeed(setSpeed75P);
                }
                break;
            case R.id.timer_speed_1_0x:
                speed = viewModel.getSpeed().getValue();
                assert speed != null;
                if(!speed.equals(setSpeed100P)){
                    viewModel.setSpeed(setSpeed100P);
                }
                break;
            case R.id.timer_speed_2_0x:
                speed = viewModel.getSpeed().getValue();
                assert speed != null;
                if(!speed.equals(setSpeed200P)){
                    viewModel.setSpeed(setSpeed200P);
                }
                break;
            case R.id.timer_speed_3_0x:
                speed = viewModel.getSpeed().getValue();
                assert speed != null;
                if(!speed.equals(setSpeed300P)){
                    viewModel.setSpeed(setSpeed300P);
                }
                break;
            case R.id.timer_speed_4_0x:
                speed = viewModel.getSpeed().getValue();
                assert speed != null;
                if(!speed.equals(setSpeed400P)){
                    viewModel.setSpeed(setSpeed400P);
                }
                break;
            default:
                return false;
        }return true;
    }

}
