package ca.sfu.fluorine.parentapp.view.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutRunningBinding;
import ca.sfu.fluorine.parentapp.model.TimeoutTimer;

/**
 * Represents the screen of the timer counting down
 */
public class TimeoutRunningFragment extends Fragment {
	private FragmentTimeoutRunningBinding binding;
	private TimeoutTimer timer;
	private Runnable playButtonAction;

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
		long millis = TimeoutRunningFragmentArgs.fromBundle(getArguments()).getDuration();
		timer = new TimeoutTimer(millis);
		timer.registerActions(this::updateTimerUI, () ->
			Navigation.findNavController(view).navigate(R.id.redirect_to_end_screen)
		);
		updateTimerUI();

		// Set up listeners for the buttons
		playButtonAction = () -> {
			timer.toggle();
			updateButtonUI();
		};

		binding.playButton.setOnClickListener((btnView) -> playButtonAction.run());

		binding.resetButton.setOnClickListener((btnView) -> {
			timer.discard();
			Navigation.findNavController(view).navigate(R.id.reset_timer_action);
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		playButtonAction.run();
	}

	// Discard the timer when the fragment is no longer visible
	@Override
	public void onStop() {
		super.onStop();
		timer.discard();
	}

	private void updateTimerUI() {
		long remainingInSeconds = timer.getRemainingTimeInMillis() / 1000;
		binding.countDownText.setText(getString(
				R.string.remaining_time,
				remainingInSeconds / 60,
				remainingInSeconds % 60));
	}

	private void updateButtonUI() {
		if (!timer.isRunning()) {
			binding.playButton.setText(R.string.resume);
		} else {
			binding.playButton.setText(R.string.pause);
		}
	}
}