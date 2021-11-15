package ca.sfu.fluorine.parentapp.view.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.sfu.fluorine.parentapp.databinding.ActivityTaskFormBinding;

public class TaskFormActivity extends AppCompatActivity {

    private ActivityTaskFormBinding binding;
    public static final int ADD_TASK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}
