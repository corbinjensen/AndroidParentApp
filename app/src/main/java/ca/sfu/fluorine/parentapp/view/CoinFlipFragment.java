package ca.sfu.fluorine.parentapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.databinding.FragmentChildrenBinding;
import ca.sfu.fluorine.parentapp.databinding.FragmentCoinFlipBinding;


public class CoinFlipFragment extends Fragment {
	private FragmentCoinFlipBinding binding;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentCoinFlipBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}
}