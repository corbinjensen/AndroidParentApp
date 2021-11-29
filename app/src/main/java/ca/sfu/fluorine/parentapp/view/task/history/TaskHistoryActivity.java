package ca.sfu.fluorine.parentapp.view.task.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.List;

import ca.sfu.fluorine.parentapp.databinding.ActivityTaskHistoryBinding;
import ca.sfu.fluorine.parentapp.databinding.TaskHistoryRowLayoutBinding;
import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;
import ca.sfu.fluorine.parentapp.viewmodel.task.TaskHistoryViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * TaskHistory.java - represents a page to view a feed that displays
 * the history of which child had a turn at completing the task.
 */
@AndroidEntryPoint
public class TaskHistoryActivity extends AppCompatActivity {
    private ActivityTaskHistoryBinding binding;
    private TaskHistoryViewModel viewModel;
    private final static String TASK_ID = "task_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TaskHistoryViewModel.class);
        int taskId = getIntent().getIntExtra(TASK_ID, 0);

        viewModel.getTaskHistoryById(taskId).observe(this, whoseTurnRecords -> {
            // if whos turn is not empty, set adapter
            if(whoseTurnRecords.isEmpty()) {
                binding.taskHistoryList.showEmpty();
            } else {
                binding.taskHistoryList.useAdapter(new TaskHistoryListAdapter(whoseTurnRecords));
            }
        });
    }

    public static Intent makeIntent(Context context, int taskId) {
        Intent intent = new Intent(context, TaskHistoryActivity.class);
        intent.putExtra(TASK_ID, taskId);
        return intent;
    }

    class TaskHistoryListAdapter extends RecyclerView.Adapter<TaskHistoryViewHolder> {
        private final List<WhoseTurnRecord> whoseTurnRecords;

        TaskHistoryListAdapter(List<WhoseTurnRecord> whoseTurnRecords) {
            this.whoseTurnRecords = whoseTurnRecords;
        }

        @NonNull
        @Override
        public TaskHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TaskHistoryRowLayoutBinding binding = TaskHistoryRowLayoutBinding.inflate(
                getLayoutInflater(), parent, false
            );
            return new TaskHistoryViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHistoryViewHolder holder, int position) {
            WhoseTurnRecord record = whoseTurnRecords.get(position);
            holder.populateHistoryData(
                TaskHistoryActivity.this,
                record,
                viewModel.getChildImageFromTaskHistory(record)
            );
        }

        @Override
        public int getItemCount() {
            return whoseTurnRecords.size();
        }
    }

}
