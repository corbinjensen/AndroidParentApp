package ca.sfu.fluorine.parentapp.view.task;

import android.content.DialogInterface;
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
import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.task.Task;
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
        binding.dropdownSelection.setText(task.getChild().getId());
        // TODO: Add edit option for child selection

        setTitle("Edit Task");
        binding.buttonSaveTask.setOnClickListener(editTaskDialogListener);
        binding.buttonDeleteTask.setVisibility(View.VISIBLE);
        binding.buttonDeleteTask.setOnClickListener(deleteTaskDialogListener);

        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);
    }

    // TODO: Fix update task & child for task
    private final View.OnClickListener editTaskDialogListener = (btnView) -> makeConfirmDialog(
            R.string.edit_task,
            R.string.edit_task_confirm,
            (dialogInterface, i) -> {
                String taskName = binding.editTaskName.getText().toString();
                String selection = binding.dropdownMenu.getTransitionName().toString();
               // task.updateTask( , );
                database.taskDao().updateTask(taskAndChild.getTask());
                finish();
            });

    private final View.OnClickListener deleteTaskDialogListener = (btnView) -> makeConfirmDialog(
            R.string.delete_task,
            R.string.delete_child_confirm,
            (dialogInterface, i) -> {
                database.taskDao().deleteTask(taskAndChild.getTask());
                finish();
            });

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
