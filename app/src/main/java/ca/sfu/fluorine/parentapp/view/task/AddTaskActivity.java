package ca.sfu.fluorine.parentapp.view.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.view.utils.ChildrenAutoCompleteAdapter;
import ca.sfu.fluorine.parentapp.view.utils.Utility;
import ca.sfu.fluorine.parentapp.viewmodel.ChildrenViewModel;

public class AddTaskActivity extends AppCompatActivity {
    ActivityTaskFormBinding binding;
    AppDatabase database;
    ChildrenAutoCompleteAdapter childrenArrayAdapter;
    ChildrenViewModel childrenViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding views
        binding = ActivityTaskFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.new_task);

        // Set up database
        database = AppDatabase.getInstance(this);

        // View models
        childrenViewModel = new ViewModelProvider(this).get(ChildrenViewModel.class);
        childrenViewModel.getChildrenLiveData().observe(this, children -> {
            if (childrenArrayAdapter == null) {
                childrenArrayAdapter =
                        new ChildrenAutoCompleteAdapter(this, children, true);
            } else {
                childrenArrayAdapter.reset(children, true);
            }
            binding.dropdownSelection.setAdapter(childrenArrayAdapter);
            setupMenuWithImages();
        });

        // Add listeners
        binding.buttonSaveTask.setOnClickListener(addTaskDialogListener);
        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);

    }

    private final View.OnClickListener addTaskDialogListener = (btnView) -> Utility.makeConfirmDialog(
            this,
            R.string.add_new_task,
            R.string.edit_task_confirm,
            (dialogInterface, i) -> {
                String taskName = binding.editTaskName.getText().toString();
                Integer childID = childrenArrayAdapter.getSelectedChild().getId();
                if(childID == Child.getUnspecifiedChild().getId()){
                    childID = null;
                }
                Task newTask = new Task(taskName, childID);
                database.taskDao().addTask(newTask);
                finish();
            }
    );

    public void setupMenuWithImages() {
        // Pre-select the first choice
        Child child = childrenArrayAdapter.getItem(0);
        childrenArrayAdapter.setSelectedChild(child);
        binding.dropdownSelection.setText(Utility.formatChildName(this, child));
        Utility.setupImage(this, binding.currentChildPhoto, child);

        // Set up the adapter and listener for the dropdown menu
        binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) -> {
            childrenArrayAdapter.setSelectedChild(childrenArrayAdapter.getItem(i));
            Utility.setupImage(this, binding.currentChildPhoto, child);
        });
    }

    final TextWatcher watcher = Utility.makeTextWatcher(() ->
            binding.buttonSaveTask.setEnabled(areAllFieldsFilled()));

    private boolean areAllFieldsFilled() {
        String taskName = binding.editTaskName.getText().toString();
        String child = binding.dropdownSelection.getText().toString();
        return !taskName.isEmpty() && !child.isEmpty();
    }
}
