package ca.sfu.fluorine.parentapp.view.task;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.databinding.FragmentTaskListBinding;
import ca.sfu.fluorine.parentapp.view.coin.CoinFlipActivity;

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
		binding = FragmentTaskListBinding
				.inflate(inflater, container, false);
		return binding.getRoot();
	}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // floating action button

        binding.buttonAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TaskFormActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    // TODO Tie this in with the Task Model
    /*
    class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
     */
}
