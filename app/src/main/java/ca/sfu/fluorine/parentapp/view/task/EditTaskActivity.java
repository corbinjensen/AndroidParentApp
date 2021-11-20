package ca.sfu.fluorine.parentapp.view.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskWithChild;

public class EditTaskActivity extends AddTaskActivity {
    private static final String TASK_ID = "taskIndex";
    private TaskWithChild taskWithChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the task id from intent then fetch from database
        List<TaskWithChild> tasks = database.taskDao().getTaskByIdWithChild(getIntent().getIntExtra(TASK_ID, 0));

        if (!tasks.isEmpty()) {
            taskWithChild = tasks.get(0);
            Child child = taskWithChild.getChild();
            childId = child.getId();
            binding.editTaskName.setText(taskWithChild.getTask().getName());
            binding.dropdownSelection.setText(getString(R.string.full_name, child.getFirstName(), child.getLastName()), false);
        }

        setTitle("Edit Task");
        binding.buttonSaveTask.setOnClickListener(editTaskDialogListener);
        binding.buttonDeleteTask.setVisibility(View.VISIBLE);
        binding.buttonCompleteTask.setVisibility(View.VISIBLE);
        binding.buttonDeleteTask.setOnClickListener(deleteTaskDialogListener);
        binding.buttonCompleteTask.setOnClickListener(confirmTaskDialogListener);

        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);
    }

    private final View.OnClickListener editTaskDialogListener = (btnView) -> makeConfirmDialog(
            R.string.edit_task,
            R.string.edit_task_confirm,
            (dialogInterface, i) -> {
                String taskName = binding.editTaskName.getText().toString();
                taskWithChild.getTask().update(taskName, childId);
                database.taskDao().updateTask(taskWithChild.getTask());
                finish();
            });

    private final View.OnClickListener deleteTaskDialogListener = (btnView) -> makeConfirmDialog(
            R.string.delete_task,
            R.string.delete_child_confirm,
            (dialogInterface, i) -> {
                database.taskDao().deleteTask(taskWithChild.getTask());
                finish();
            });

    private final View.OnClickListener confirmTaskDialogListener = (btnView) -> makeConfirmDialog(
            R.string.complete_task,
            R.string.complete_task_message,
            (dialogInterface, i) -> {
                Task task = taskWithChild.getTask();
                int nextChildID = database.childDao().getNextChildId(taskWithChild.getChild());
                task.update(task.getName(), nextChildID);
                database.taskDao().updateTask(task);
                finish();
            });


    @NonNull
    public static Intent makeIntent(Context context, int taskId) {
        Intent intent = new Intent(context, EditTaskActivity.class);
        intent.putExtra(TASK_ID, taskId);
        return intent;
    }
}
