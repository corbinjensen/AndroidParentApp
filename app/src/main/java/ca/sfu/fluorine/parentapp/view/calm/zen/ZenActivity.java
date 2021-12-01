package ca.sfu.fluorine.parentapp.view.calm.zen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.sfu.fluorine.parentapp.databinding.ActivityZenBinding;

public class ZenActivity extends AppCompatActivity {
    private ActivityZenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}