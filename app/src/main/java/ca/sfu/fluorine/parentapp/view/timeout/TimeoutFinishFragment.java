package ca.sfu.fluorine.parentapp.view.timeout;

import android.media.MediaPlayer;
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
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutFinishBinding;
import ca.sfu.fluorine.parentapp.model.TimeoutSetting;
import ca.sfu.fluorine.parentapp.service.RingtoneController;
import ca.sfu.fluorine.parentapp.view.utils.NoActionBarFragment;

/**
 * Represents the end screen when the timer reaches 0
 */
public class TimeoutFinishFragment extends NoActionBarFragment {
	private RingtoneController ringtoneController;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return FragmentTimeoutFinishBinding
				.inflate(inflater, container, false)
				.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Add listener to the button
		Button dismissButton = FragmentTimeoutFinishBinding.bind(view).button;
		dismissButton.setOnClickListener(btnView -> {
			TimeoutSetting.getInstance(getContext()).clear();
			Navigation.findNavController(view).navigate(R.id.return_to_timeout);
		});

		// Play the sound
		ringtoneController = new RingtoneController(requireContext(), R.raw.jingle);
		ringtoneController.playSound();
		ringtoneController.vibrate();
	}

	@Override
	public void onStop() {
		super.onStop();
		if (ringtoneController != null) {
			ringtoneController.cancelAll();
			ringtoneController = null;
		}
	}
}