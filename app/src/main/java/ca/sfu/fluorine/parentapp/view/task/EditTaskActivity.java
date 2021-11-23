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

public class EditTaskActivity extends AddTaskActivity {
    private static final String TASK_ID = "taskIndex";
    private TaskAndChild taskAndChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.task_details);

        binding.editTaskName.removeTextChangedListener(watcher);
        binding.dropdownSelection.removeTextChangedListener(watcher);

        // Get the task id from intent then fetch from database
        List<TaskAndChild> tasks = database.taskDao()
                .getTaskByIdWithChild(getIntent().getIntExtra(TASK_ID, 0));

        // Populate the data
        if (!tasks.isEmpty()) {
            taskAndChild = tasks.get(0);
            Child child = taskAndChild.getChild();
            if (child == null) {
                child = Child.getUnspecifiedChild();
                binding.dropdownSelection.setText(R.string.no_children);
                binding.buttonCompleteTask.setVisibility(View.GONE);
                childrenArrayAdapter.setSelectedChild(Child.getUnspecifiedChild());
            } else {
                binding.dropdownSelection.setText(
                        getString(R.string.full_name, child.getFirstName(), child.getLastName()),
                        false);
                updateImage(child);
                binding.buttonCompleteTask.setVisibility(View.VISIBLE);
                childrenArrayAdapter.setSelectedChild(child);
            }
            binding.editTaskName.setText(taskAndChild.getTask().getName());
        }

        // Show the hidden buttons
        binding.buttonSaveTask.setOnClickListener(editTaskDialogListener);
        binding.buttonDeleteTask.setVisibility(View.VISIBLE);

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
                Integer nullChild = childrenArrayAdapter.getSelectedChild().getId();
                if(nullChild == Child.getUnspecifiedChild().getId()){
                    nullChild = null;
                }
                taskAndChild.getTask().update(taskName, nullChild);
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

    private final View.OnClickListener confirmTaskDialogListener = (btnView) -> {
        makeConfirmDialog(
                R.string.complete_task,
                R.string.complete_task_message,
                (dialogInterface, i) -> {
                    Task task = taskAndChild.getTask();
                    Integer nextChildID;
                    if(taskAndChild.getChild() == null){
                        nextChildID = database.childDao().getNextChildId(taskAndChild.getChild());
                    }else{
                        nextChildID = null;
                    }
                    task.update(task.getName(), nextChildID);
                    database.taskDao().updateTask(task);
                    finish();
                });
    };


    @NonNull
    public static Intent makeIntent(Context context, int taskId) {
        Intent intent = new Intent(context, EditTaskActivity.class);
        intent.putExtra(TASK_ID, taskId);
        return intent;
    }
}
