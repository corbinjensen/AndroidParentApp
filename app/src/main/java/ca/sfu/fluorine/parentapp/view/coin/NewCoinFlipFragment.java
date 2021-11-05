package ca.sfu.fluorine.parentapp.view.coin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentNewCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.children.ChildrenManager;

public class NewCoinFlipFragment extends Fragment {
	private FragmentNewCoinFlipBinding binding;

	@Override
	public void onAttach(@NonNull Context context) {
		if (ChildrenManager.getInstance(context).getChildren().isEmpty()) {
			NavHostFragment.findNavController(this)
					.navigate(R.id.flipping_coin_action);
		}
		super.onAttach(context);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentNewCoinFlipBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}
}