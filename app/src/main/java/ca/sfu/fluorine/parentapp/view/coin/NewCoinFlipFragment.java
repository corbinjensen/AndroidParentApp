package ca.sfu.fluorine.parentapp.view.coin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentNewCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.view.utils.ChildrenAutoCompleteAdapter;

/**
 * NewCoinFlipFragment
 *
 * Represents the UI to set up a new coin flip.
 */
public class NewCoinFlipFragment extends Fragment {
	private FragmentNewCoinFlipBinding binding;
	private int childId = -1;
	private List<Child> children;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppDatabase database = AppDatabase.getInstance(requireContext().getApplicationContext());
		children = database.childDao().getAllChildrenOrderByRecentCoinFlips();
		if (children.isEmpty()) {
			NavHostFragment.findNavController(this)
					.navigate(R.id.flipping_coin_action);
		} else {
			children.add(Child.getUnspecifiedChild());
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentNewCoinFlipBinding.inflate(inflater, container, false);
		setupMenuWithImages();
		setupButton();
		return binding.getRoot();
	}

	public void setupMenuWithImages() {
		ChildrenAutoCompleteAdapter childrenArrayAdapter = new ChildrenAutoCompleteAdapter(
				requireContext(), children);
		// Pre-select the first choice
		Child first = children.get(0);
		childrenArrayAdapter.setSelectedChild(first);
		binding.dropdownSelection.setText(
				getString(R.string.full_name, first.getFirstName(), first.getLastName()),
				false);
		childId = first.getId();

		// Set up the adapter and listener for the dropdown menu
		binding.dropdownSelection.setAdapter(childrenArrayAdapter);
		binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) -> {
			childId = children.get(i).getId();
			childrenArrayAdapter.setSelectedChild(children.get(i));
		});
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
