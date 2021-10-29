package ca.sfu.fluorine.parentapp.view.timeout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.DialogCustomTimerBinding;
import ca.sfu.fluorine.parentapp.model.TimeoutTimer;

/**
 * Represents a dialog to start a timer with customized duration
 */
public class CustomTimerDialog extends AppCompatDialogFragment {
	private DialogCustomTimerBinding binding;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		initialize();

		return new AlertDialog.Builder(getActivity())
				.setView(binding.getRoot())
				.setTitle(R.string.custom_timer)
				.setNegativeButton(android.R.string.cancel, (dialog, i) -> dialog.cancel())
				.setPositiveButton(R.string.start, (dialog, i) -> {
					startTimer();
					dialog.dismiss();
				})
				.create();
	}

	private void initialize() {
		binding = DialogCustomTimerBinding.inflate(getLayoutInflater());
		NumberPicker picker = binding.customDurationPicker;
		picker.setMinValue(getResources().getInteger(R.integer.min_duration));
		picker.setMaxValue(getResources().getInteger(R.integer.max_duration));
		updateUI(picker.getValue());

		picker.setOnValueChangedListener(
				(numberPicker, oldValue, newValue) -> updateUI(newValue));
	}

	private void updateUI(int value) {
		binding.minutes.setText(getResources().getQuantityString(R.plurals.minutes, value));
	}

	private void startTimer() {
		CustomTimerDialogDirections.RunCustomTimerAction action
				= CustomTimerDialogDirections.runCustomTimerAction();
		long durationInMillis
				= binding.customDurationPicker.getValue() * TimeoutTimer.MINUTES_TO_MILLIS;
		action.setDuration(durationInMillis);
		NavHostFragment.findNavController(this).navigate(action);
	}
}
