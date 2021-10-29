package ca.sfu.fluorine.parentapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.CoinFlipActivity;
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
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		binding.navigationCoinFlip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent add = new Intent(getContext(), CoinFlipActivity.class);
				startActivity(add);
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}
}