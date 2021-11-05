package ca.sfu.fluorine.parentapp.view.coin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentNewCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.children.ChildrenManager;

public class NewCoinFlipFragment extends Fragment {
	private FragmentNewCoinFlipBinding binding;
	private ChildrenManager manager;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = ChildrenManager.getInstance(requireContext());
		if (manager.getChildren().isEmpty()) {
			NavHostFragment.findNavController(this)
					.navigate(R.id.flipping_coin_action);
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentNewCoinFlipBinding.inflate(inflater, container, false);
		setupMenu();
		return binding.getRoot();
	}

	private void setupMenu() {
		// Set up children menu
		// TODO: Replace dummy values with real data
		String[] strings = new String[]{"John", "Jane", "Mary"};
		ArrayAdapter<String> childArray = new ArrayAdapter<>(
				requireContext(),
				R.layout.children_menu_item, strings);
		binding.dropdownSelection.setAdapter(childArray);
		binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) ->
			binding.flipButton.setEnabled(true)
		);
	}

}