package ca.sfu.fluorine.parentapp.view.coin;

import android.graphics.Bitmap;
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
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;
import ca.sfu.fluorine.parentapp.view.utils.ChildrenAutoCompleteAdapter;

/**
 * NewCoinFlipFragment
 *
 * Represents the UI to set up a new coin flip.
 */
public class NewCoinFlipFragment extends Fragment {
	private FragmentNewCoinFlipBinding binding;
	private ChildrenAutoCompleteAdapter childrenArrayAdapter;
	private ImageInternalStorage storage;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppDatabase database = AppDatabase.getInstance(requireContext());
		List<Child> children = database.childDao().getAllChildrenOrderByRecentCoinFlips();
		if (children.isEmpty()) {
			NavHostFragment.findNavController(this)
					.navigate(R.id.flipping_coin_action);
		} else {
			children.add(Child.getUnspecifiedChild());
		}
		childrenArrayAdapter = new ChildrenAutoCompleteAdapter(requireContext(), children);
		storage = ImageInternalStorage.getInstance(requireContext());
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
		// Pre-select the first choice
		Child first = childrenArrayAdapter.getItem(0);
		childrenArrayAdapter.setSelectedChild(first);
		binding.dropdownSelection.setText(
				getString(R.string.full_name, first.getFirstName(), first.getLastName()),
				false);
		binding.dropdownSelection.setAdapter(childrenArrayAdapter);
		updateCurrentImage();

		// Add listener
		binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) -> {
			childrenArrayAdapter.setSelectedChild(childrenArrayAdapter.getItem(i));
			updateCurrentImage();
		});
	}

	private void setupButton() {
		View.OnClickListener listener = (btnView) -> {
			boolean isHead = binding.coinSideSelection.getCheckedButtonId() == R.id.head;
			NewCoinFlipFragmentDirections.FlippingCoinAction action =
					NewCoinFlipFragmentDirections
							.flippingCoinAction()
							.setChildId(childrenArrayAdapter.getSelectedChild().getId())
							.setCoinSide(isHead);
			NavHostFragment.findNavController(this).navigate(action);
		};
		binding.flipButton.setOnClickListener(listener);
	}

	private void updateCurrentImage() {
		Child child = childrenArrayAdapter.getSelectedChild();
		if (child.getId() == Child.getUnspecifiedChild().getId()) {
			binding.currentChild.setVisibility(View.INVISIBLE);
		} else {
			binding.currentChild.setVisibility(View.VISIBLE);
			Bitmap bm = storage.loadImage(child.getPhotoFileName());
			if (bm == null) {
				binding.currentChild.setImageResource(R.drawable.robot);
			} else {
				binding.currentChild.setImageBitmap(bm);
			}
		}
	}
}
