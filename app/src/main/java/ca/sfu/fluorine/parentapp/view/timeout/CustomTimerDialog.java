package ca.sfu.fluorine.parentapp.view.timeout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.function.Consumer;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.DialogCustomTimerBinding;

public class CustomTimerDialog extends AppCompatDialogFragment {
	private DialogCustomTimerBinding binding;
	private Consumer<Integer> startTimerAction;

	private CustomTimerDialog() {
		super();
	}

	public CustomTimerDialog(@NonNull Consumer<Integer> startTimer) {
		this();
		this.startTimerAction = startTimer;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		initializeView();
		dialogBuilder
				.setView(binding.getRoot())
				.setTitle(R.string.custom_timer)
		.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {})
		.setPositiveButton(R.string.start, (dialogInterface, i) ->
			startTimerAction.accept(binding.customDurationPicker.getValue()));
		return dialogBuilder.create();
	}

	private void initializeView() {
		binding = DialogCustomTimerBinding.inflate(getLayoutInflater());
		NumberPicker picker = binding.customDurationPicker;
		picker.setMinValue(getResources().getInteger(R.integer.min_duration));
		picker.setMaxValue(getResources().getInteger(R.integer.max_duration));
		updateUI(picker.getValue());

		picker.setOnValueChangedListener((numberPicker, oldValue, newValue) -> updateUI(newValue));
	}

	private void updateUI(int value) {
		binding.minutes.setText(getResources().getQuantityString(R.plurals.minutes, value));
	}
}
