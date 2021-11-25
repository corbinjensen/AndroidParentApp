package ca.sfu.fluorine.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import ca.sfu.fluorine.parentapp.databinding.ActivityMainBinding;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * MainActivity.java
 *
 * Represents the UI for main activity of the Parenting App.
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_children,
				R.id.navigation_coin_flip,
				R.id.navigation_timeout,
				R.id.navigation_task)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.navView, navController);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
	    if(item.getItemId()==R.id.help) {
            Intent helpLink = new Intent(this, HelpActivity.class);
            startActivity(helpLink);
        }
        return super.onOptionsItemSelected(item);
    }
}
