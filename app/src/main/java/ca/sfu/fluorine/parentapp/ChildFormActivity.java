package ca.sfu.fluorine.parentapp;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import ca.sfu.fluorine.parentapp.databinding.ActivityChildFormBinding;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildrenManager;
import ca.sfu.fluorine.parentapp.view.ChildrenFragment;

/**
 *  ChildFormActivity.java - represents a user input form
 *  activity to add a new, or modify info on a child.
 */

public class ChildFormActivity extends AppCompatActivity {
    private ActivityChildFormBinding binding;
    private static final String CHILD_INDEX = "childIndex";
    public static final int ADD_CHILD = -1;
    private ChildrenManager manager;
    private int childIndex = ADD_CHILD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = ChildrenManager.getInstance(this);

        childIndex = getIntent().getIntExtra(CHILD_INDEX, -1);
        if (childIndex >= 0) {
            // Populate the field
            Child child = manager.getChildByIndex(childIndex);
            binding.editTextFirstName.setText(child.getFirstName());
            binding.editTextLastName.setText(child.getLastName());

            setupActionBar(R.string.edit_child);
            binding.buttonAddChild.setOnClickListener(editChildrenDialogListener);
        } else {
            setupActionBar(R.string.add_new_child);
            binding.buttonAddChild.setOnClickListener(addChildrenDialogListener);

        }

        binding.editTextFirstName.addTextChangedListener(watcher);
        binding.editTextLastName.addTextChangedListener(watcher);
    }

    private void setupActionBar(@StringRes int titleId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleId);
        }
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
                .setNegativeButton(android.R.string.cancel, (dialog, i) -> {
                    dialog.dismiss();
                }).show();
    }

    public static Intent makeIntent(Context context, int index) {
        Intent intent = new Intent(context, ChildFormActivity.class);
        intent.putExtra(CHILD_INDEX, index);
        return intent;
    }

    private final View.OnClickListener addChildrenDialogListener = (btnView) -> {
        makeConfirmDialog(
            R.string.addChild,
            R.string.addChild,
            (dialogInterface, i) -> {
                String firstName = binding.editTextFirstName.getText().toString();
                String lastName = binding.editTextLastName.getText().toString();
                manager.addChild(firstName, lastName);
                finish();
            }
        );
    };

    private final View.OnClickListener editChildrenDialogListener = (btnView) -> makeConfirmDialog(
        R.string.edit_child,
        R.string.edit_child,
        (dialogInterface, i) -> {
            String firstName = binding.editTextFirstName.getText().toString();
            String lastName = binding.editTextLastName.getText().toString();
            manager.modifyChild(childIndex, firstName, lastName);
            finish();
        });

    private final View.OnClickListener deleteChildDialogListener = (btnView) -> makeConfirmDialog(
        R.string.delete_child,
        R.string.delete_child,
        (dialogInterface, i) -> {
            manager.deleteChild(childIndex);
            finish();
        });
}
