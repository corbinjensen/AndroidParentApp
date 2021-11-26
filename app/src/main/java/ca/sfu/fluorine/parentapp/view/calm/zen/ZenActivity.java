package ca.sfu.fluorine.parentapp.view.calm.zen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.sfu.fluorine.parentapp.databinding.ActivityZenBinding;

public class ZenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityZenBinding binding = ActivityZenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}