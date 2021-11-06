package ca.sfu.fluorine.parentapp.view.utils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NoActionBarFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();

        // Hide the action bar on start
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        // Show the action bar when done
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }
}
