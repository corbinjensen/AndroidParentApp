package ca.sfu.fluorine.parentapp.view.calm.timeout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.ActivityTimeoutBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TimeoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTimeoutBinding binding = ActivityTimeoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the action bar to display the fragment labels of the navigation graph
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder().build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_timeout);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
}