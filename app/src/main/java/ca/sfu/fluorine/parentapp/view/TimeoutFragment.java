package ca.sfu.fluorine.parentapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutBinding;
import ca.sfu.fluorine.parentapp.model.TimeoutTimer;


public class TimeoutFragment extends Fragment {
	private FragmentTimeoutBinding binding;
	private TimeoutTimer timer;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentTimeoutBinding.inflate(inflater, container, false);

		timer = makeTimer(1);
		timer.registerAction(this::updateTimerUI, () -> {
			updateButtonUI();
			updateTimerUI();
		});
		updateTimerUI();

		// Set up listeners for the buttons
		binding.playButton.setOnClickListener((view) -> {
			timer.toggle();
			updateButtonUI();
		});

		binding.resetButton.setOnClickListener((view) -> timer.reset());

		return binding.getRoot();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

	private void updateTimerUI() {
		long remainingInSeconds = timer.getRemainingTimeInMillis() / 1000;
		binding.countDownText.setText(getString(
				R.string.remaining_time,
				remainingInSeconds / 60,
				remainingInSeconds % 60));
	}

	private void updateButtonUI() {
		binding.resetButton.setEnabled(!timer.isPristine());
		if (!timer.isRunning()) {
			binding.playButton.setText(timer.isPristine() ? R.string.start : R.string.pause);
		} else {
			binding.playButton.setText(R.string.stop);
		}
	}

	private TimeoutTimer makeTimer(int minutes) {
		TimeoutTimer timer = new TimeoutTimer(minutes);
		timer.registerAction(this::updateTimerUI, () -> {
			updateTimerUI();
			updateButtonUI();
		});
		return timer;
	}
}