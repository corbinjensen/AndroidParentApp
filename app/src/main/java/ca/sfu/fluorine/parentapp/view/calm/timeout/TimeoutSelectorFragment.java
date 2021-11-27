package ca.sfu.fluorine.parentapp.view.calm.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutSelectorBinding;
import ca.sfu.fluorine.parentapp.viewmodel.timeout.TimeoutLiteViewModel;
import ca.sfu.fluorine.parentapp.viewmodel.timeout.TimeoutViewModel;

/**
 * Represents the screen for timer selection
 *
 * Users can either have quick access to preset duration
 * or start their own custom timer
 */
public class TimeoutSelectorFragment extends Fragment {
	private TimeoutLiteViewModel viewModel;

	private static final LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT,
			1.0f
	);

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Navigate away to running timer when a timer is active
		viewModel = new ViewModelProvider(this).get(TimeoutLiteViewModel.class);
		if (viewModel.hasSavedTimer()) {
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
			String buttonLabel = getResources()
					.getQuantityString(R.plurals.duration_in_minutes, duration, duration);
			View.OnClickListener listener = btnView -> {
				// Store the selection to the internal storage
				viewModel.initializeTimeout(duration * 60000L);
				Navigation.findNavController(view)
						.navigate(R.id.start_preset_timer_action);
			};

			buttonList.addView(createButton(buttonLabel, listener));
		}

		// Add a button for custom data
		Button customButton = createButton(getString(R.string.custom_timer), btnView ->
			Navigation.findNavController(view).navigate(R.id.show_custom_dialog_action)
		);
		buttonList.addView(customButton);
	}

	@NonNull
	private Button createButton(String buttonLabel, View.OnClickListener listener) {
		MaterialButton button = new MaterialButton(
				new ContextThemeWrapper(requireContext(), R.style.CustomButton_WhiteOutlined));
		button.setLayoutParams(param);
		button.setText(buttonLabel);
		button.setOnClickListener(listener);
		return button;
	}
}
