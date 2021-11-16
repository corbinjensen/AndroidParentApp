package ca.sfu.fluorine.parentapp.view.children;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityChildFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.service.CropImageService;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;

/**
 * AddChildActivity.java - represents a user input form
 * activity to add a new, or modify info on a child.
 */
public class AddChildActivity extends AppCompatActivity {
    ActivityChildFormBinding binding;
    Bitmap icon = null;

    // For the database and storage
    AppDatabase database;
    ImageInternalStorage imageStorage;
    private CropImageService cropImageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the database
        database = AppDatabase.getInstance(getApplicationContext());

        // Set up the image storage
        imageStorage = ImageInternalStorage.getInstance(getApplicationContext());

        // Add watcher to the fields
        binding.editTextFirstName.addTextChangedListener(watcher);
        binding.editTextLastName.addTextChangedListener(watcher);

        setTitle(R.string.add_new_child);
        binding.buttonAddChild.setOnClickListener(addChildrenDialogListener);

        // Set up crop image service
        cropImageService = new CropImageService(this);
    }

    final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.buttonAddChild.setEnabled(areAllFieldsFilled());
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };

    private boolean areAllFieldsFilled() {
        String firstName = binding.editTextFirstName.getText().toString();
        String lastName = binding.editTextLastName.getText().toString();
        return !firstName.isEmpty() && !lastName.isEmpty();
    }

    void makeConfirmDialog(@StringRes int titleId,
                           @StringRes int messageId,
                           @NonNull DialogInterface.OnClickListener confirmAction) {
        new AlertDialog.Builder(this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, confirmAction)
                .setNegativeButton(android.R.string.cancel, (dialog, i) -> dialog.dismiss())
                .show();
    }

    private final View.OnClickListener addChildrenDialogListener = (btnView) -> makeConfirmDialog(
            R.string.add_new_child,
            R.string.edit_child_confirm,
            (dialogInterface, i) -> {
                String firstName = binding.editTextFirstName.getText().toString();
                String lastName = binding.editTextLastName.getText().toString();
                Child newChild = new Child(firstName, lastName, null);
                persistIconData(newChild);
                database.childDao().addChild(newChild);
                finish();
            }
    );

    // Listeners for user choose image from camera or gallery
    public void onChangeIconButtonClicked(View view) {
        cropImageService.launch((Bitmap resultImage) -> {
            // TODO: Replace current image with result image only (if image is not null)
        });
    }

    public void onDeleteIconButtonClicked(View view) {
        icon = null;
        // TODO: Change the image view to default only
    }

    public void persistIconData(@NonNull Child child) {
        // Get the filename from the child model. If none exists, make a random name
        String fileName = child.getPhotoFileName();

        // If no such file exists, make a random one
        if(fileName == null && icon != null) {
            fileName = UUID.randomUUID().toString();
        }

        if (icon == null) {
            imageStorage.deleteImage(fileName);
        } else {
            imageStorage.saveImage(fileName, icon);
        }
    }

}
