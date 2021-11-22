package ca.sfu.fluorine.parentapp.view.children;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.UUID;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

public class EditChildActivity extends AddChildActivity {
	// For intent data
	private static final String CHILD_ID = "childIndex";
	public static final int DEFAULT = -1;

	private Child child;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.edit_child);

		// Populate the data
		int childId = getIntent().getIntExtra(CHILD_ID, DEFAULT);
		child = viewModel.getChildById(childId);
		if (child != null) {
			// Remove watcher before set text
			binding.editTextFirstName.removeTextChangedListener(watcher);
			binding.editTextLastName.removeTextChangedListener(watcher);

			binding.editTextFirstName.setText(child.getFirstName());
			binding.editTextLastName.setText(child.getLastName());

			// Remove watcher before set text
			binding.editTextFirstName.addTextChangedListener(watcher);
			binding.editTextLastName.addTextChangedListener(watcher);

			// Initial display child image
			Utility.setupImage(this, binding.displayChildImage, child);
		}

		// Activate more buttons
		binding.buttonSaveChild.setOnClickListener(editChildrenDialogListener);
		binding.buttonDeleteChild.setVisibility(View.VISIBLE);
		binding.buttonDeleteChild.setOnClickListener(deleteChildDialogListener);
	}

	public static Intent makeIntent(Context context, int childId) {
		Intent intent = new Intent(context, EditChildActivity.class);
		intent.putExtra(CHILD_ID, childId);
		return intent;
	}

	private final View.OnClickListener editChildrenDialogListener = (btnView) -> Utility.makeConfirmDialog(
			this,
			R.string.edit_child,
			R.string.edit_child_confirm,
			(dialogInterface, i) -> {
				String savedFile = persistIconData(child);
				String firstName = binding.editTextFirstName.getText().toString();
				String lastName = binding.editTextLastName.getText().toString();
				child.updateName(firstName, lastName);
				child.updatePhotoFileName(savedFile);
				viewModel.updateChild(child);
				finish();
			});

	private final View.OnClickListener deleteChildDialogListener = (btnView) -> Utility.makeConfirmDialog(
			this,
			R.string.delete_child,
			R.string.delete_child_confirm,
			(dialogInterface, i) -> {
				imageStorage.deleteImage(child.getPhotoFileName());
				viewModel.deleteChild(child);
				finish();
			});

	String persistIconData(@NonNull Child child) {
		String filename = child.getPhotoFileName();
		if (icon == null) {
			imageStorage.deleteImage(filename);
			return null;
		}
		if (filename == null) {
			filename = UUID.randomUUID().toString();
		}
		imageStorage.saveImage(filename, icon);
		return filename;
	}
}
