package ca.sfu.fluorine.parentapp.view.children;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityChildFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.service.CropImageService;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

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
    private ActivityResultLauncher<?> cropImageServiceLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the database
        database = AppDatabase.getInstance(this);

        // Set up the image storage
        imageStorage = ImageInternalStorage.getInstance(this);

        // Add watcher to the fields
        binding.editTextFirstName.addTextChangedListener(watcher);
        binding.editTextLastName.addTextChangedListener(watcher);

        setTitle(R.string.add_new_child);
        binding.buttonSaveChild.setOnClickListener(addChildrenDialogListener);

        // Set up crop image service
        cropImageServiceLauncher = new CropImageService(this).getServiceLauncher(
                (Bitmap resultImage) -> {
                    if (resultImage != null) {
                        icon = resultImage;
                        binding.displayChildImage.setImageBitmap(resultImage);
                        binding.deleteChildImage.setEnabled(true);
                        binding.buttonSaveChild.setEnabled(areAllFieldsFilled());
                    }
                });
    }

    final TextWatcher watcher = Utility.makeTextWatcher(() ->
            binding.buttonSaveChild.setEnabled(areAllFieldsFilled()));

    private boolean areAllFieldsFilled() {
        String firstName = binding.editTextFirstName.getText().toString();
        String lastName = binding.editTextLastName.getText().toString();
        return !firstName.isEmpty() && !lastName.isEmpty();
    }

    private final View.OnClickListener addChildrenDialogListener = (btnView) -> Utility.makeConfirmDialog(
            this,
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
        cropImageServiceLauncher.launch(null);
    }

    public void onDeleteIconButtonClicked(View btnView) {
        icon = null;
        binding.displayChildImage.setImageResource(R.drawable.default_icon);
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
