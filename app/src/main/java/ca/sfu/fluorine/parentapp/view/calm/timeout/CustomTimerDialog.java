package ca.sfu.fluorine.parentapp.view.calm.timeout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.DialogCustomTimerBinding;
import ca.sfu.fluorine.parentapp.viewmodel.TimeoutViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Represents a dialog to start a timer with customized duration
 */
@AndroidEntryPoint
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
		long duration = binding.customDurationPicker.getValue() * 60000L;
		TimeoutViewModel viewModel = new ViewModelProvider(this).get(TimeoutViewModel.class);
		viewModel.initializeTimeout(duration);
		NavHostFragment.findNavController(this).navigate(R.id.run_custom_timer_action);
	}
}
