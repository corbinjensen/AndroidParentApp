package ca.sfu.fluorine.parentapp.view.task;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

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

        // TODO: Set child in edit task
    }
}
