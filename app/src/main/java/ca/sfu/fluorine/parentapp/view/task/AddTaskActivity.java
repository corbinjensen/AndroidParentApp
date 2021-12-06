package ca.sfu.fluorine.parentapp.view.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;

import java.util.Objects;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.view.utils.ChildrenAutoCompleteAdapter;
import ca.sfu.fluorine.parentapp.view.utils.Utility;
import ca.sfu.fluorine.parentapp.viewmodel.children.ChildrenListingViewModel;
import ca.sfu.fluorine.parentapp.viewmodel.task.TaskEditViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddTaskActivity extends AppCompatActivity {
    ActivityTaskFormBinding binding;
    ChildrenAutoCompleteAdapter childrenArrayAdapter;
    ChildrenListingViewModel childrenViewModel;
    TaskEditViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding views
        binding = ActivityTaskFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.new_task);

        // Child View models
        ViewModelProvider provider = new ViewModelProvider(this);

        // Task view model
        taskViewModel = provider.get(TaskEditViewModel.class);

        // Add listeners
        binding.buttonSaveTask.setOnClickListener(addTaskDialogListener);
        binding.editTaskName.addTextChangedListener(watcher);
        binding.dropdownSelection.addTextChangedListener(watcher);

        childrenViewModel = provider.get(ChildrenListingViewModel.class);
        populateDropDown(Child.getUnspecifiedChild());
    }

    private final View.OnClickListener addTaskDialogListener = (btnView) -> Utility.makeConfirmDialog(
            this,
            R.string.add_new_task,
            R.string.edit_task_confirm,
            (dialogInterface, i) -> {
                String taskName = Utility.getTextFromInput(binding.editTaskName);
                Integer childID = childrenArrayAdapter.getSelectedChild().getId();
                if(Objects.equals(childID, Child.getUnspecifiedChild().getId())){
                    childID = null;
                }
                Task newTask = new Task(taskName, childID);
                taskViewModel.addTask(newTask);
                finish();
            }
    );

    final TextWatcher watcher = Utility.makeTextWatcher(() ->
            binding.buttonSaveTask.setEnabled(areAllFieldsFilled()));

    private boolean areAllFieldsFilled() {
        String taskName = Utility.getTextFromInput(binding.editTaskName);
        String child = binding.dropdownSelection.getText().toString();
        return !taskName.isEmpty() && !child.isEmpty();
    }

    void populateDropDown(@NonNull Child selectedChild) {
        childrenViewModel.getChildrenLiveData().observe(this, children -> {
            if (childrenArrayAdapter == null) {
                childrenArrayAdapter =
                        new ChildrenAutoCompleteAdapter(this, children, childrenViewModel);
            }
            binding.dropdownSelection.setAdapter(childrenArrayAdapter);
            childrenArrayAdapter.setSelectedChild(selectedChild);
            binding.dropdownSelection.setText(
                    Utility.formatChildName(this, selectedChild), false);
            Utility.setupImage(
                    selectedChild, childrenViewModel.loadBitmapFrom(selectedChild), binding.currentChildPhoto);
        });

        // Set up the adapter and listener for the dropdown menu
        binding.dropdownSelection.setOnItemClickListener((adapterView, view, i, l) -> {
            Child child = childrenArrayAdapter.getItem(i);
            childrenArrayAdapter.setSelectedChild(child);
            Utility.setupImage(child, childrenViewModel.loadBitmapFrom(child), binding.currentChildPhoto);
        });
    }
}
