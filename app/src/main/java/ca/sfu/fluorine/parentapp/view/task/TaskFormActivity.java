package ca.sfu.fluorine.parentapp.view.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskAndChild;

public class TaskFormActivity extends AppCompatActivity {
    private static final String CHILD_ID = "childIndex";
    public static final int ADD_TASK = 0;
    public static final int ADD_CHILD = -1;

    private ActivityTaskFormBinding binding;
    private AppDatabase database;
    private Task task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("New Task");

        database = AppDatabase.getInstance(getApplicationContext());

        List<Task> tasks = database.taskDao().getChildById(
                getIntent().getIntExtra(CHILD_ID, ADD_TASK)
        );



    }
}
