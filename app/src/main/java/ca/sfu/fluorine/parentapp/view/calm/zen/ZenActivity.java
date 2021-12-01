package ca.sfu.fluorine.parentapp.view.calm.zen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import ca.sfu.fluorine.parentapp.databinding.ActivityZenBinding;
import ca.sfu.fluorine.parentapp.viewmodel.zen.BreathingViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ZenActivity extends AppCompatActivity {
    private ActivityZenBinding binding;
    private BreathingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityZenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(BreathingViewModel.class);
    }
}