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

import java.util.ArrayList;
import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentNewCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * NewCoinFlipFragment
 *
 * Represents the UI to set up a new coin flip.
 */
public class NewCoinFlipFragment extends Fragment {
	private FragmentNewCoinFlipBinding binding;
	private final static int NO_CHILDREN_ID = -1;
	private int childId = NO_CHILDREN_ID;
	private List<Child> children;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppDatabase database = AppDatabase.getInstance(requireContext().getApplicationContext());
		children = database.childDao().getAllChildrenOrderByRecentCoinFlips();
		if (children.isEmpty()) {
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
		setupButton();
		return binding.getRoot();
	}

	private void setupMenu() {
		// Create the content for the menu
		List<String> childrenSelection = new ArrayList<>();
		childrenSelection.add(getString(R.string.no_children));
		for (final Child child: children) {
			String itemName = requireContext()
					.getString(R.string.full_name, child.getFirstName(), child.getLastName());
			childrenSelection.add(itemName);
		}

		// Attach the content to the dropdown menu
		ArrayAdapter<String> childArray = new ArrayAdapter<>(
				requireContext(),
				R.layout.children_menu_item, childrenSelection);
		binding.dropdownSelection.setAdapter(childArray);
		binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) ->
				childId = (i == 0) ? NO_CHILDREN_ID : children.get(i-1).getId());

		// Select the second values as default
		if (children.size() > 1) {
			childId = children.get(0).getId();
			binding.dropdownSelection.setText(childrenSelection.get(1), false);
		}

	}

	private void setupButton() {
		View.OnClickListener listener = (btnView) -> {
			boolean isHead = binding.coinSideSelection.getCheckedButtonId() == R.id.head;
			NewCoinFlipFragmentDirections.FlippingCoinAction action =
					NewCoinFlipFragmentDirections
							.flippingCoinAction()
							.setChildId(childId).setCoinSide(isHead);
			NavHostFragment.findNavController(this).navigate(action);
		};
		binding.flipButton.setOnClickListener(listener);
	}
}
