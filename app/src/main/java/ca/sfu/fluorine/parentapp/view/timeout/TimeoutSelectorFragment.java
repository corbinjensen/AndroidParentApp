package ca.sfu.fluorine.parentapp.view.timeout;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutSelectorBinding;
import ca.sfu.fluorine.parentapp.model.TimeoutSetting;
import ca.sfu.fluorine.parentapp.model.TimeoutTimer;
import ca.sfu.fluorine.parentapp.view.timeout.TimeoutSelectorFragmentDirections.StartPresetTimerAction;

/**
 * Represents the screen for timer selection
 *
 * Users can either have quick access to preset duration
 * or start their own custom timer
 */
public class TimeoutSelectorFragment extends Fragment {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Navigate away to running timer when a timer is active
		TimeoutSetting setting = TimeoutSetting.getInstance(getContext());
		Long expiredTime = setting.getExpiredTime();
		if (expiredTime != null) {
			NavHostFragment.findNavController(this)
					.navigate(R.id.start_preset_timer_action);
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		return FragmentTimeoutSelectorBinding
				.inflate(inflater, container, false)
				.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final LinearLayout buttonList = FragmentTimeoutSelectorBinding.bind(view).buttonList;

		// Add buttons for preset durations
		int[] presetDurations = getResources().getIntArray(R.array.preset_durations);
		for (final int duration : presetDurations) {
			buttonList.addView(createPresetButton(view, duration));
		}

		// Add a button for custom data
		Button customButton = new Button(getContext());
		customButton.setText(R.string.custom_timer);
		customButton.setOnClickListener(btnView ->
			Navigation.findNavController(view).navigate(R.id.show_custom_dialog_action)
		);
		buttonList.addView(customButton);
	}

	public Button createPresetButton(View view, int minutes) {
		Button button = new Button(getContext());
		String buttonLabel = getResources()
				.getQuantityString(R.plurals.duration_in_minutes, minutes, minutes);
		button.setText(buttonLabel);
		button.setOnClickListener(btnView -> {
			StartPresetTimerAction action =
					TimeoutSelectorFragmentDirections.startPresetTimerAction();
			long expiredTime = Calendar.getInstance().getTimeInMillis()
					+ minutes * TimeoutTimer.MINUTES_TO_MILLIS;
			action.setExpiredTime(expiredTime);
			Navigation.findNavController(view)
					.navigate(action);
		});
		return button;
	}

	// TODO: Make this screen more beautiful
}
