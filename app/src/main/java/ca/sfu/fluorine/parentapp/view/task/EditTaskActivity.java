package ca.sfu.fluorine.parentapp.view.task;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.task.TaskAndChild;

public class EditTaskActivity extends AddTaskActivity {
    private static final String CHILD_ID = "childIndex";
    private static final String TASK_ID = "taskIndex";
    private TaskAndChild taskAndChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the task id from intent then fetch from database
        List<TaskAndChild> tasks = database.taskDao().getTaskByIdWithChild(getIntent()
                .getIntExtra(TASK_ID, 0));
        taskAndChild = tasks.get(0);

        binding.editTaskName.setText(task.getTask().getName());
        binding.buttonDeleteTask.setVisibility(View.VISIBLE);
        setTitle("Edit Child");

        taskAndChild = tasks.get(0);
        binding.editTaskName.setText(taskAndChild.getTask().getName());

        // TODO: Add edit option for child selection

        binding.buttonSaveTask.setOnClickListener(editChildrenDialogListener);
        binding.buttonDeleteTask.setVisibility(View.VISIBLE);
        binding.buttonDeleteTask.setOnClickListener(deleteChildDialogListener);

        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);
    }

    // TODO: Fix update task & child for task
    private final View.OnClickListener editChildrenDialogListener = (btnView) -> makeConfirmDialog(
            R.string.edit_child,
            R.string.edit_child_confirm,
            (dialogInterface, i) -> {
                String taskName = binding.editTaskName.getText().toString();
                String lastName = binding.editTextLastName.getText().toString();
                child.updateName(firstName, lastName);
                persistIconData(child);
                database.childDao().updateChild(child);
                finish();
            });

    private final TextWatcher watcher = new TextWatcher() {
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
