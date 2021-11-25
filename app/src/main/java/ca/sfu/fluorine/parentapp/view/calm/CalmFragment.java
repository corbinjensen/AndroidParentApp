package ca.sfu.fluorine.parentapp.view.calm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.databinding.FragmentCalmBinding;
import ca.sfu.fluorine.parentapp.view.calm.timeout.TimeoutActivity;

/**
 * Represents the calm screens, consisting of two features buttons
 * "Timeout" and "Zen"
 */
public class CalmFragment extends Fragment {
    private FragmentCalmBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCalmBinding.inflate(inflater, container, false);

        // Add listeners to the button
        binding.timeoutButton.setOnClickListener(btnView -> {
            Intent i = new Intent(requireContext(), TimeoutActivity.class);
            startActivity(i);
        });

        return binding.getRoot();
    }
}