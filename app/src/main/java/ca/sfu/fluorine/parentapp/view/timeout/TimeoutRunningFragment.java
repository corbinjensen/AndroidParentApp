package ca.sfu.fluorine.parentapp.view.timeout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Calendar;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutRunningBinding;
import ca.sfu.fluorine.parentapp.model.TimeoutSetting;
import ca.sfu.fluorine.parentapp.model.TimeoutTimer;
import ca.sfu.fluorine.parentapp.model.TimeoutTimer.TimerState;
import ca.sfu.fluorine.parentapp.service.TimeoutExpiredReceiver;

/**
 * Represents the screen of the timer counting down
 */
public class TimeoutRunningFragment extends Fragment {
	private FragmentTimeoutRunningBinding binding;
	private TimeoutTimer timer;
	private TimeoutSetting timeoutSetting;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		timeoutSetting = TimeoutSetting.getInstance(getContext());
	}

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

		// Set up timer from the argument
		long millis = TimeoutRunningFragmentArgs.fromBundle(getArguments()).getExpiredTime();
		timer = new TimeoutTimer(millis);
		timer.registerActions(this::updateTimerUI, () ->
			Navigation.findNavController(view).navigate(R.id.redirect_to_end_screen)
		);

		updateTimerUI();
		updateButtonUI();

		binding.playButton.setOnClickListener((btnView) -> toggleTimer());

		binding.resetButton.setOnClickListener((btnView) -> {
			timer.discard();
			Navigation.findNavController(view).navigate(R.id.reset_timer_action);
		});

		if (timeoutSetting.isTimerRunning()) {
			toggleTimer();
		}
	}

	// Discard the timer when the fragment is no longer visible
	@Override
	public void onStop() {
		super.onStop();

		// Save the timer if it's not finished
		if (timer.getState() != TimerState.FINISHED) {
			timeoutSetting.saveTimer(timer);
		} else {
			timeoutSetting.clear();
		}
		timer.discard();
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
		if (timer.getState() == TimerState.PAUSED) {
			binding.playButton.setText(R.string.resume);
		} else {
			binding.playButton.setText(R.string.pause);
		}
	}
}