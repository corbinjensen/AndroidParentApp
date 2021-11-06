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
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildrenManager;

public class NewCoinFlipFragment extends Fragment {
	private FragmentNewCoinFlipBinding binding;
	private ChildrenManager manager;
	private ArrayList<Integer> childIndices;
	private int childId = -1;

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
		setupButton();
		return binding.getRoot();
	}

	private void setupMenu() {
		// Create the content for the menu
		List<Child> childrenList = manager.getChildren();
		int lastChildId = manager.getLastChildId();
		childIndices = new ArrayList<>();
		List<String> childNames = new ArrayList<>();
		for (int i = 0; i < childrenList.size(); i++) {
			if (lastChildId != i) {
				childIndices.add(i);
				Child child = childrenList.get(i);
				String childName = requireContext()
						.getString(R.string.full_name, child.getFirstName(), child.getLastName());
				childNames.add(childName);
			}
		}

		// Attach the content to the dropdown menu
		ArrayAdapter<String> childArray = new ArrayAdapter<>(
				requireContext(),
				R.layout.children_menu_item, childNames);
		binding.dropdownSelection.setAdapter(childArray);
		binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) -> {
			binding.flipButton.setEnabled(true);
			childId = childIndices.get(i);
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
