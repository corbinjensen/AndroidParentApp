package ca.sfu.fluorine.parentapp.view.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.sfu.fluorine.parentapp.databinding.ActivityTaskHistoryBinding;

/**
 * TaskHistory.java - represents a page to view a feed that displays
 * the history of which child had a turn at completing the task.
 */
public class TaskHistory extends AppCompatActivity {
    ActivityTaskHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
