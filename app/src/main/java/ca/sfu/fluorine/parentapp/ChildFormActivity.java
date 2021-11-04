package ca.sfu.fluorine.parentapp;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.sfu.fluorine.parentapp.databinding.ActivityChildFormBinding;

/**
 *  ChildFormActivity.java - represents a user input form
 *  activity to add a new, or modify info on a child.
 */

public class ChildFormActivity extends AppCompatActivity {
    private ActivityChildFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.add_new_child);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // The confirm action is lambda expression () -> { some code }
    private void makeConfirmDialog(@StringRes int titleId,
                                   @StringRes int messageId,
                                   @NonNull Runnable confirmAction) {
        new AlertDialog.Builder(this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(android.R.string.ok, (dialog, i) -> {
                    confirmAction.run();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, i) -> {
                    dialog.dismiss();
                }).show();
    }
}
