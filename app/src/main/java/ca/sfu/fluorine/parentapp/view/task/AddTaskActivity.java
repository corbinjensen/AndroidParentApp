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
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskAndChild;

public class AddTaskActivity extends AppCompatActivity {
    ActivityTaskFormBinding binding;
    AppDatabase database;
    private List<Child> children;
    private final static int NO_CHILDREN_ID = -1;
    int childId = NO_CHILDREN_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = AppDatabase.getInstance(getApplicationContext());
        children = database.childDao().getAllChildren();
        setupMenu();

        binding.buttonSaveTask.setOnClickListener(addTaskDialogListener);

        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);

    }

    final View.OnClickListener addTaskDialogListener = (btnView) -> makeConfirmDialog(
            R.string.add_new_task,
            R.string.edit_task_confirm,
            (dialogInterface, i) -> {
                String taskName = binding.editTaskName.getText().toString();
                Task newTask = new Task(taskName, childId);
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

    private void setupMenu() {
        // Create the content for the menu
        List<String> childrenSelection = new ArrayList<>();
        childrenSelection.add(getString(R.string.no_children));
        for (final Child child: children) {
            String itemName = getString(R.string.full_name, child.getFirstName(), child.getLastName());
            childrenSelection.add(itemName);
        }

        // Attach the content to the dropdown menu
        ArrayAdapter<String> childArray = new ArrayAdapter<>(
                this,
                R.layout.children_menu_item, childrenSelection);
        binding.dropdownSelection.setAdapter(childArray);
        binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) ->
                childId = (i == 0) ? NO_CHILDREN_ID : children.get(i-1).getId());

        // Select the second values as default
        if (children.size() > 1) {
            childId = children.get(0).getId();
            binding.dropdownSelection.setText(childrenSelection.get(1), false);
        }

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
        public void afterTextChanged(Editable editable) { }
    };

    private boolean areAllFieldsFilled() {
        String taskName = binding.editTaskName.getText().toString();
        String child = binding.dropdownSelection.getText().toString();
        return !taskName.isEmpty() && !child.isEmpty();
    }
}
