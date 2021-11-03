package ca.sfu.fluorine.parentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.sfu.fluorine.parentapp.databinding.ActivityMainBinding;
import ca.sfu.fluorine.parentapp.model.Child;
import ca.sfu.fluorine.parentapp.model.ChildrenManager;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private final ChildListAdapter adapter = new ChildListAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());


		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_children, R.id.navigation_coin_flip, R.id.navigation_timeout)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.navView, navController);

        RecyclerView childList = findViewById(R.id.childrenList);
        childList.setAdapter(adapter);
        childList.setLayoutManager(new LinearLayoutManager(this));

	}


    class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder> {
        private final ChildrenManager childrenManager = ChildrenManager.getInstance();
        private final Context context;


        public ChildListAdapter(Context context) {
            this.context = context;

            // TODO - These are example children to show the list.
            childrenManager.addChild("Hello","world");
            childrenManager.addChild("Sally","Smith");
            childrenManager.addChild("Brenden","Johnson");
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView titleCreationName;
            CardView childCard;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                // Find all the components of the view.
                titleCreationName =  itemView.findViewById(R.id.childNameDisplay);
                childCard = itemView.findViewById(R.id.childCard);
            }
        }

        @NonNull
        @Override
        public ChildListAdapter.ViewHolder onCreateViewHolder(
            @NonNull
                ViewGroup parent,
            int viewType
        ) {
            View view = LayoutInflater.from(context).inflate(
                R.layout.child_row_layout,
                parent,
                false
            );
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(
            @NonNull
                ChildListAdapter.ViewHolder holder,
            int position
        ) {
            // get child object from index
            Child child = childrenManager.getChildFromIndex(position);

            // change the text to display childs name
            holder.titleCreationName.setText(child.getFullName());

            // make the list item clickable
            // TODO - change to edit activity.
            holder.itemView.setOnClickListener((View view) -> {

            });
        }

        @Override
        public int getItemCount() {
            return childrenManager.getAllChildren().size();
        }
    }

}
