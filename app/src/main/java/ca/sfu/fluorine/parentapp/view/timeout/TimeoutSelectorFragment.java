package ca.sfu.fluorine.parentapp.view.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutSelectorBinding;
import ca.sfu.fluorine.parentapp.view.timeout.TimeoutSelectorFragmentDirections.*;

public class TimeoutSelectorFragment extends Fragment {
	private FragmentTimeoutSelectorBinding binding;
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		binding = FragmentTimeoutSelectorBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Add buttons for preset durations
		int[] presetDurations = getResources().getIntArray(R.array.preset_durations);
		for (final int duration : presetDurations) {
			binding.buttonList.addView(createPresetButton(duration));
		}

		// Add a button for custom data
		Button customButton = new Button(getContext());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

	public Button createPresetButton(int minutes) {
		Button button = new Button(getContext());
		String buttonLabel = getResources()
				.getQuantityString(R.plurals.duration_in_minutes, minutes, minutes);
		button.setText(buttonLabel);
		button.setOnClickListener(view -> startTimer(minutes));
		return button;
	}

	public void startTimer(int minutes) {
		StartTimerAction action = TimeoutSelectorFragmentDirections.startTimerAction();
		action.setDuration(minutes);
		Navigation.findNavController(binding.getRoot()).navigate(action);
	}
}
