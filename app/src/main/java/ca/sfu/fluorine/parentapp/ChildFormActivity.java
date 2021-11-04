package ca.sfu.fluorine.parentapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

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
        binding.editTextFirstName.addTextChangedListener(watcher);
        binding.editTextLastName.addTextChangedListener(watcher);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.add_new_child);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        Log.d(null, firstName);
        Log.d(null, lastName);
        return !firstName.isEmpty() && !lastName.isEmpty();
    }
}
