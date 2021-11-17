package ca.sfu.fluorine.parentapp.view.children;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;
import java.util.UUID;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityChildFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;

/**
 *  ChildFormActivity.java - represents a user input form
 *  activity to add a new, or modify info on a child.
 */
//TODO : Put the code changes from this code intoAddCChildActivity, delete this.
public class ChildFormActivity extends AppCompatActivity {
    private ActivityChildFormBinding binding;
    private Bitmap icon = null;

    // For intent data
    private static final String CHILD_ID = "childIndex";
    public static final int ADD_CHILD = -1;

    // For the database and storage
    private AppDatabase database;
    private Child child = null;
    private ImageInternalStorage imageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the database
        database = AppDatabase.getInstance(getApplicationContext());

        // Set up the image storage
        imageStorage = ImageInternalStorage.getInstance(getApplicationContext());

        // Populate the data
        List<Child> children = database.childDao().getChildById(
                getIntent().getIntExtra(CHILD_ID, ADD_CHILD)
        );
        if (!children.isEmpty()) {
            // Edit mode
            child = children.get(0);
            binding.editTextFirstName.setText(child.getFirstName());
            binding.editTextLastName.setText(child.getLastName());

            setTitle(R.string.edit_child);
            binding.buttonAddChild.setOnClickListener(editChildrenDialogListener);
            binding.buttonDeleteChild.setVisibility(View.VISIBLE);
            binding.buttonDeleteChild.setOnClickListener(deleteChildDialogListener);

            // TODO: Set up the icon

        } else {
            // Add mode
            setTitle(R.string.add_new_child);
            binding.buttonAddChild.setOnClickListener(addChildrenDialogListener);
        }

        binding.editTextFirstName.addTextChangedListener(watcher);
        binding.editTextLastName.addTextChangedListener(watcher);

        // Set up launcher for Crop Image Activity
        cropImageActivityLauncher =
                registerForActivityResult(cropImageActivityContract, cropImageActivityCallback);
    }

    // For the image selection
    private ActivityResultLauncher<Object> cropImageActivityLauncher;
    private final ActivityResultContract<Object, Uri> cropImageActivityContract
            = new ActivityResultContract<Object, Uri>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Object input) {
            return CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAllowFlipping(false)
                    .setAllowRotation(false)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .getIntent(context);
        }

        @Override
        public Uri parseResult(int resultCode, @Nullable Intent intent) {
            CropImage.ActivityResult result = CropImage.getActivityResult(intent);
            if (result == null) return null;
            return result.getUri();
        }
    };


    private final ActivityResultCallback<Uri> cropImageActivityCallback = (Uri uri) -> {
        // Tasks after getting cropped image
        try {
            icon = MediaStore.Images.Media.
                    getBitmap(getApplicationContext().getContentResolver(), uri);
            binding.buttonAddChild.setEnabled(areAllFieldsFilled());
            // TODO: Update the icon image view only

        } catch (Exception e) {
            Toast.makeText(
                    ChildFormActivity.this,
                    R.string.fetch_error,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

    private final TextWatcher watcher = new TextWatcher() {
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

    private void makeConfirmDialog(@StringRes int titleId,
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

    private void makeSelectImageDialogue(@StringRes int titleId,
                                         @StringRes int arrayListedImageOptions,
                                         @NonNull DialogInterface.OnClickListener selectAction){

        // TODO : Create Dialogue box -> select one of 3 options(from phone, camera, cancel)
        //TODO : implement list of options

        new AlertDialog.Builder(this)
                .setTitle(titleId)
                .setItems(arrayListedImageOptions, selectAction){
            public void onClick(selectAction, int which){
                if (which == 0){
                    //for index 0 action
                }else if(which == 1){
                    //for index 1 action
                }else{
                    //for index 2 action
                }
            }
        }

    }

    public static Intent makeIntent(Context context, int index) {
        Intent intent = new Intent(context, ChildFormActivity.class);
        intent.putExtra(CHILD_ID, index);
        return intent;
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

    private final View.OnClickListener editChildrenDialogListener = (btnView) -> makeConfirmDialog(
            R.string.edit_child,
            R.string.edit_child_confirm,
            (dialogInterface, i) -> {
                String firstName = binding.editTextFirstName.getText().toString();
                String lastName = binding.editTextLastName.getText().toString();
                child.updateName(firstName, lastName);
                persistIconData(child);
                database.childDao().updateChild(child);
                finish();
            });

    private final View.OnClickListener deleteChildDialogListener = (btnView) -> makeConfirmDialog(
            R.string.delete_child,
            R.string.delete_child_confirm,
            (dialogInterface, i) -> {
                database.childDao().deleteChild(child);
                finish();
            });

    // Listeners for user choose image from camera or gallery
    public void onChangeIconButtonClicked(View view) {
        // TODO : Dialogue - take pic, select from stored, cancel

        // TODO : Open already loaded in-app images, option to select more from gallery
        // TODO : Transfer image file(s) into in-app storage
        // TODO : Save specific image into child. return to child form activity.

        cropImageActivityLauncher.launch(null);
    }

    public void onDeleteIconButtonClicked(View view) {
        icon = null;
        // TODO: Change the image view to default only
    }

    public void persistIconData(@NonNull Child child) {
        //Get the filename from the child model. If none exists, make a random name
        String fileName = child.getPhotoFileName();

        if(fileName == null){
            fileName = UUID.randomUUID().toString();
        }

        if (icon == null) {
            imageStorage.deleteImage(fileName);
        } else {
            imageStorage.saveImage(fileName, icon);
        }
    }
}
