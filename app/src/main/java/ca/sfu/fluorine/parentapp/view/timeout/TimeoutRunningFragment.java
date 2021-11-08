package ca.sfu.fluorine.parentapp.view.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutRunningBinding;
import ca.sfu.fluorine.parentapp.model.timeout.TimeoutSetting;
import ca.sfu.fluorine.parentapp.model.timeout.TimeoutTimer;
import ca.sfu.fluorine.parentapp.model.timeout.TimeoutTimer.TimerState;
import ca.sfu.fluorine.parentapp.service.BackgroundTimeoutService;
import ca.sfu.fluorine.parentapp.service.TimeoutExpiredNotification;
import ca.sfu.fluorine.parentapp.view.utils.NoActionBarFragment;

/**
 * Represents the screen of the timer counting down
 */
public class TimeoutRunningFragment extends NoActionBarFragment {
    private FragmentTimeoutRunningBinding binding;
    private TimeoutTimer timer;
    private TimeoutSetting timeoutSetting;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTimeoutRunningBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timeoutSetting = TimeoutSetting.getInstance(requireContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        BackgroundTimeoutService.removeAlarm(requireContext());
        TimeoutExpiredNotification.hideNotification(requireContext());
        binding.pulsator.start();

        // Set up the timer
        timer = timeoutSetting.makeTimer();
        if (timer == null) {
            long millis = TimeoutRunningFragmentArgs.fromBundle(getArguments()).getExpiredTime();
            timer = new TimeoutTimer(millis);
        }

        timer.registerActions(this::updateTimerUI, () ->
            NavHostFragment.findNavController(this)
                .navigate(R.id.redirect_to_end_screen)
        );

        updateTimerUI();
        updateButtonUI();

        binding.playButton.setOnClickListener((btnView) -> toggleTimer());

        binding.resetButton.setOnClickListener((btnView) -> {
            timer.discard();
            NavHostFragment.findNavController(this).navigate(R.id.reset_timer_action);
        });

        if (timeoutSetting.isTimerRunning()) {
            toggleTimer();
        }
    }

    // Discard the timer when the fragment is no longer visible
    @Override
    public void onStop() {
        TimerState state = timer.getState();
        // Save the timer if it's not finished
        if (state != TimerState.FINISHED) {
            timeoutSetting.saveTimer(timer);
            if (state == TimerState.RUNNING) {
                BackgroundTimeoutService.setAlarm(requireContext(), timer);
            }
        } else {
            timeoutSetting.clear();
        }
        timer.discard();

        super.onStop();
    }

    private void toggleTimer() {
        timer.toggle();
        updateButtonUI();
    }

    private void updateTimerUI() {
        long remainingInSeconds = timer.getMillisLeft() / 1000;
        binding.countDownText.setText(getString(
            R.string.remaining_time,
            remainingInSeconds / 60,
            remainingInSeconds % 60));
    }

    private void updateButtonUI() {
        binding.playButton.setText(
            timer.getState() == TimerState.PAUSED ? R.string.resume : R.string.pause);
    }
}
