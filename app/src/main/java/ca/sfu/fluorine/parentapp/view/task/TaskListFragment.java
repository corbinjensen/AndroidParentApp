package ca.sfu.fluorine.parentapp.view.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTaskListBinding;
import ca.sfu.fluorine.parentapp.view.children.ChildFormActivity;

/**
 * TaskListFragment.java - Represents a home screen and feed/list of tasks
 *  user can create a new task, or edit existing ones.
 */
public class TaskListFragment extends Fragment {

    private FragmentTaskListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		FragmentTaskListBinding binding = FragmentTaskListBinding
				.inflate(inflater, container, false);
		return binding.getRoot();
	}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // floating action button
        // TODO add Intent to new taskForm Activity
        binding.buttonAddTask.setOnClickListener(
            btnView -> {
                startActivity(
                    ChildFormActivity.makeIntent(
                        requireContext(),
                        TaskFormActivity.ADD_TASK
                    )
                );
            });
    }
}
