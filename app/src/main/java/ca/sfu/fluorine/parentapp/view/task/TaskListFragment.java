package ca.sfu.fluorine.parentapp.view.task;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTaskListBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultAndChild;
import ca.sfu.fluorine.parentapp.model.task.TaskAndChild;
import ca.sfu.fluorine.parentapp.view.children.ChildFormActivity;
import ca.sfu.fluorine.parentapp.view.children.ChildrenFragment;
import ca.sfu.fluorine.parentapp.view.coin.CoinFlipViewHolder;


/**
 * TaskListFragment.java - Represents a home screen and feed/list of tasks
 *  user can create a new task, or edit existing ones.
 */
public class TaskListFragment extends Fragment {
    private AppDatabase database;

    private FragmentTaskListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getInstance(requireContext().getApplicationContext());
    }

    @Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentTaskListBinding
				.inflate(inflater, container, false);
		return binding.getRoot();
	}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // floating action button

        binding.buttonAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddTaskActivity.class);
            startActivity(intent);
        });

        // Set up layout for the list
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.taskList.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration =
            new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        binding.taskList.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.taskList.setAdapter(new TaskListAdapter());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {
        private final List<TaskAndChild> tasks;

        public TaskListAdapter() {
            tasks = database.taskDao().getAllTasksWithChildren();
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(
            @NonNull
                ViewGroup parent,
            int viewType
        ) {
            View view = LayoutInflater.from(requireContext()).inflate(
                R.layout.task_row_layout,
                parent,
                false
            );
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(
            @NonNull
                TaskViewHolder taskHolder,
            int position
        ) {
            // get child object from index
            TaskAndChild task = tasks.get(position);

            taskHolder.populateData(requireContext().getApplicationContext(), task);

            // make the list item clickable
            taskHolder.itemView.setOnClickListener((View view) -> {
                Intent intent = new Intent(getContext(), TaskFormActivity.class);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }


}
