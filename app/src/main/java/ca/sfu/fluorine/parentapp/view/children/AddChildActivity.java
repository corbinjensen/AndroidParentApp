package ca.sfu.fluorine.parentapp.view.children;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                String savedImageFileName = persistIconData();
                Child newChild = new Child(firstName, lastName, savedImageFileName);
                database.childDao().addChild(newChild);
                finish();
            }
    );

    // Listeners for user choose image from camera or gallery
    public void onChangeIconButtonClicked(View btnView) {
        cropImageService.launch((Bitmap resultImage) -> {
                if(resultImage != null){
                    icon = resultImage;
                }
            });
    }

    public void onDeleteIconButtonClicked(View btnView) {
        icon = null;
        // Disabled this button as no photo to delete
        btnView.setEnabled(false);
    }

    // Return a saved image filename in the system
    String persistIconData() {
        if (icon != null) {
            String filename = UUID.randomUUID().toString();
            imageStorage.saveImage(filename, icon);
            return filename;
        }
        return null;
    }
}
