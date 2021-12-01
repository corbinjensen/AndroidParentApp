package ca.sfu.fluorine.parentapp.view.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.view.task.history.TaskHistoryActivity;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

public class EditTaskActivity extends AddTaskActivity {
    private static final String TASK_ID = "taskIndex";
    private int taskId;
    private TaskWithChild taskWithChild;
    private Child previousChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.task_details);

        // Unregister watcher
        binding.editTaskName.removeTextChangedListener(watcher);
        binding.dropdownSelection.removeTextChangedListener(watcher);

        // Get the task id from intent then fetch from database
        taskId = getIntent().getIntExtra(TASK_ID, 0);
        taskWithChild = taskViewModel.getTaskByIdWithChild(taskId);

        // Populate the data
        if (taskWithChild != null) {
            binding.editTaskName.setText(taskWithChild.getTask().getName());
            previousChild = taskWithChild.getChild();
            if (previousChild == null) {
                previousChild = Child.getUnspecifiedChild();
            } else {
                binding.buttonCompleteTask.setVisibility(View.VISIBLE);
                binding.buttonCompleteTask.setOnClickListener(confirmTaskDialogListener);
            }
            populateDropDown(previousChild);
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
                taskWithChild.getTask().update(taskName, childrenArrayAdapter.getSelectedChild().getId());
                taskViewModel.updateTask(taskWithChild.getTask());
                finish();
            });

    private final View.OnClickListener deleteTaskDialogListener = (btnView) -> Utility.makeConfirmDialog(
            this,
            R.string.delete_task,
            R.string.delete_child_confirm,
            (dialogInterface, i) -> {
                taskViewModel.deleteTask(taskWithChild.getTask());
                finish();
            });

    private final View.OnClickListener confirmTaskDialogListener = (btnView) -> {
        Child nextChild = childrenViewModel.getNextChild(previousChild);
        String childName = Utility.formatChildName(EditTaskActivity.this, nextChild);
        String completeTaskMessage = getString(R.string.complete_task_message, childName);
        Utility.createConfirmDialogBuilder(EditTaskActivity.this)
                .setTitle(R.string.complete_task)
                .setMessage(completeTaskMessage)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    Task task = taskWithChild.getTask();

                    // Push the child who completed the task to the history
                    Integer childId = previousChild.getId();
                    if (childId != null) {
                        taskViewModel.addChildrenToTaskHistory(task.getId(), childId);
                    }

                    // Update the next turn fot the task
                    task.update(task.getName(), nextChild.getId());
                    taskViewModel.updateTask(task);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.show_history) {
            Intent i = TaskHistoryActivity.makeIntent(this, taskId);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
