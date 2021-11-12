package ca.sfu.fluorine.parentapp.view.children;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityChildFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 *  ChildFormActivity.java - represents a user input form
 *  activity to add a new, or modify info on a child.
 */
public class ChildFormActivity extends AppCompatActivity {
    private ActivityChildFormBinding binding;

    // For intent data
    private static final String CHILD_ID = "childIndex";
    public static final int ADD_CHILD = -1;

    // For the database
    private AppDatabase database;
    private Child child = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the database
        database = AppDatabase.getInstance(getApplicationContext());

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
        } else {
            // Add mode
            setTitle(R.string.add_new_child);
            binding.buttonAddChild.setOnClickListener(addChildrenDialogListener);

        }

        binding.editTextFirstName.addTextChangedListener(watcher);
        binding.editTextLastName.addTextChangedListener(watcher);
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

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

    public static Intent makeIntent(Context context, int index) {
        Intent intent = new Intent(context, ChildFormActivity.class);
        intent.putExtra(CHILD_ID, index);
        return intent;
    }

    private final View.OnClickListener addChildrenDialogListener = (btnView) -> makeConfirmDialog(
            R.string.addChild,
            R.string.addChild,
            (dialogInterface, i) -> {
                String firstName = binding.editTextFirstName.getText().toString();
                String lastName = binding.editTextLastName.getText().toString();
                database.childDao().addChild(new Child(firstName, lastName));
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
}
