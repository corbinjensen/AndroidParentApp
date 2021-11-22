package ca.sfu.fluorine.parentapp.view.task;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.view.utils.ChildrenAutoCompleteAdapter;

public class AddTaskActivity extends AppCompatActivity {
    ActivityTaskFormBinding binding;
    AppDatabase database;
    private List<Child> children;
    ChildrenAutoCompleteAdapter childrenArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding views
        binding = ActivityTaskFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.new_task);

        // Set up database
        database = AppDatabase.getInstance(this);
        children = database.childDao().getAllChildren();

        // Set up the menu
        childrenArrayAdapter = new ChildrenAutoCompleteAdapter(this, children);
        setupMenuWithImages();

        // Add listeners
        binding.buttonSaveTask.setOnClickListener(addTaskDialogListener);
        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);

    }

    final View.OnClickListener addTaskDialogListener = (btnView) -> makeConfirmDialog(
            R.string.add_new_task,
            R.string.edit_task_confirm,
            (dialogInterface, i) -> {
                String taskName = binding.editTaskName.getText().toString();
                Task newTask = new Task(taskName, childrenArrayAdapter.getSelectedChild().getId());
                database.taskDao().addTask(newTask);
                finish();
            }
    );

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

    public void setupMenuWithImages() {
        // Pre-select the first choice
        selectChildAt(0);

        // Set up the adapter and listener for the dropdown menu
        binding.dropdownSelection.setAdapter(childrenArrayAdapter);
        binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) ->
                childrenArrayAdapter.setSelectedChild(childrenArrayAdapter.getItem(i))
        );
    }

    void selectChildAt(int position) {
        Child child = childrenArrayAdapter.getItem(position);
        childrenArrayAdapter.setSelectedChild(child);
        binding.dropdownSelection.setText(
                getString(R.string.full_name, child.getFirstName(), child.getLastName()),
                false);
    }

    final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.buttonSaveTask.setEnabled(areAllFieldsFilled());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private boolean areAllFieldsFilled() {
        String taskName = binding.editTaskName.getText().toString();
        String child = binding.dropdownSelection.getText().toString();
        return !taskName.isEmpty() && !child.isEmpty();
    }
}
