package ca.sfu.fluorine.parentapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.databinding.FragmentCoinAnimationBinding;

public class NewCoinFlipFragment extends Fragment {
	private FragmentCoinAnimationBinding binding;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentCoinAnimationBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}
}