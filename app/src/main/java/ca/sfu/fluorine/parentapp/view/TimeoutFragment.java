package ca.sfu.fluorine.parentapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutBinding;


public class TimeoutFragment extends Fragment {
	private FragmentTimeoutBinding binding;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentTimeoutBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}
}