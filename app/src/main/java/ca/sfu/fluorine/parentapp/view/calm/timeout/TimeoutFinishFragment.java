package ca.sfu.fluorine.parentapp.view.calm.timeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutFinishBinding;
import ca.sfu.fluorine.parentapp.service.RingtoneController;
import ca.sfu.fluorine.parentapp.service.TimeoutExpiredNotification;
import ca.sfu.fluorine.parentapp.view.utils.NoActionBarFragment;
import ca.sfu.fluorine.parentapp.viewmodel.timeout.TimeoutLiteViewModel;
import ca.sfu.fluorine.parentapp.viewmodel.timeout.TimeoutViewModel;

/**
 * Represents the end screen when the timer reaches 0
 */
public class TimeoutFinishFragment extends NoActionBarFragment {
	private RingtoneController ringtoneController;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TimeoutExpiredNotification.hideNotification(requireContext());
		new ViewModelProvider(this).get(TimeoutLiteViewModel.class).clear();
	}

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
		// Clear out the saved timer and navigate away
		Button dismissButton = FragmentTimeoutFinishBinding.bind(view).button;
		dismissButton.setOnClickListener(btnView ->
			NavHostFragment.findNavController(this).navigate(R.id.return_to_timeout)
		);
	}

	@Override
	public void onStart() {
		super.onStart();
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