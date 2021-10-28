package ca.sfu.fluorine.parentapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutSelectorBinding;
import ca.sfu.fluorine.parentapp.view.TimeoutSelectorFragmentDirections.*;

public class TimeoutSelectorFragment extends Fragment {
	private FragmentTimeoutSelectorBinding binding;
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		binding = FragmentTimeoutSelectorBinding.inflate(inflater, container, false);

		binding.timerSelection.setMinValue(getResources().getInteger(R.integer.min_duration));
		binding.timerSelection.setMaxValue(getResources().getInteger(R.integer.max_duration));
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		binding.buttonStart.setOnClickListener((button) -> {
			StartTimerAction action = TimeoutSelectorFragmentDirections.startTimerAction();
			action.setDuration(binding.timerSelection.getValue());
			Navigation.findNavController(view).navigate(action);
		});
	}
}
