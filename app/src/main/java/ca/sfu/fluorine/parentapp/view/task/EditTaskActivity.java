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
import ca.sfu.fluorine.parentapp.model.task.TaskAndChild;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

public class EditTaskActivity extends AddTaskActivity {
    private static final String TASK_ID = "taskIndex";
    private TaskAndChild taskAndChild;
    private Child previousChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.task_details);

        // Unregister watcher
        binding.editTaskName.removeTextChangedListener(watcher);
        binding.dropdownSelection.removeTextChangedListener(watcher);

        // Get the task id from intent then fetch from database
        int taskId = getIntent().getIntExtra(TASK_ID, 0);
        taskAndChild = database.taskDao().getTaskByIdWithChild(taskId);

        // Populate the data
        if (taskAndChild != null) {
            binding.editTaskName.setText(taskAndChild.getTask().getName());
            previousChild = taskAndChild.getChild();
            if (previousChild == null) {
                previousChild = Child.getUnspecifiedChild();
            } else {
                binding.buttonCompleteTask.setVisibility(View.VISIBLE);
                binding.buttonCompleteTask.setOnClickListener(confirmTaskDialogListener);
            }
            String childName = Utility.formatChildName(this, previousChild);
            binding.dropdownSelection.setText(childName, false);
            childrenArrayAdapter.setSelectedChild(previousChild);
            Utility.setupImage(this, binding.currentChildPhoto, previousChild);
        }

        // Set up more buttons
        binding.buttonSaveTask.setOnClickListener(editTaskDialogListener);
        binding.buttonDeleteTask.setVisibility(View.VISIBLE);
        binding.buttonDeleteTask.setOnClickListener(deleteTaskDialogListener);

        // Re-register watcher
        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);
    }

    private final View.OnClickListener editTaskDialogListener = (btnView) -> Utility.makeConfirmDialog(
            this,
            R.string.edit_task,
            R.string.edit_task_confirm,
            (dialogInterface, i) -> {
                String taskName = binding.editTaskName.getText().toString();
                taskAndChild.getTask().update(taskName, childrenArrayAdapter.getSelectedChild().getId());
                database.taskDao().updateTask(taskAndChild.getTask());
                finish();
            });

    private final View.OnClickListener deleteTaskDialogListener = (btnView) -> Utility.makeConfirmDialog(
            this,
            R.string.delete_task,
            R.string.delete_child_confirm,
            (dialogInterface, i) -> {
                database.taskDao().deleteTask(taskAndChild.getTask());
                finish();
            });

    private final View.OnClickListener confirmTaskDialogListener = (btnView) -> {
        Child nextChild = database.childDao().getNextChildId(previousChild);
        String childName = Utility.formatChildName(EditTaskActivity.this, nextChild);
        String completeTaskMessage = getString(R.string.complete_task_message, childName);
        Utility.createConfirmDialogBuilder(EditTaskActivity.this)
                .setTitle(R.string.complete_task)
                .setMessage(completeTaskMessage)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    Task task = taskAndChild.getTask();
                    task.update(task.getName(), nextChild.getId());
                    database.taskDao().updateTask(task);
                    finish();
                })
                .show();
    };

    @NonNull
    public static Intent makeIntent(Context context, int taskId) {
        Intent intent = new Intent(context, EditTaskActivity.class);
        intent.putExtra(TASK_ID, taskId);
        return intent;
    }
}
