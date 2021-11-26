package ca.sfu.fluorine.parentapp.view.calm.timeout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ca.sfu.fluorine.parentapp.databinding.ActivityTimeoutBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TimeoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTimeoutBinding binding = ActivityTimeoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}