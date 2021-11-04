package ca.sfu.fluorine.parentapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ca.sfu.fluorine.parentapp.ChildFormActivity;
import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentChildrenBinding;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildrenManager;

/**
 * ChildrenFragment.java - represents the UI of the configure children feature.
 */
public class ChildrenFragment extends Fragment {
	private FragmentChildrenBinding binding;
	private ChildrenManager manager;
	private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        manager = ChildrenManager.getInstance(context);
    }

    @Override
	public View onCreateView(
	    @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
		// Inflate the layout for this fragment
		binding = FragmentChildrenBinding.inflate(inflater, container, false);
		return binding.getRoot();

    }

    /** Called when the user taps the Send button */


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // floating action button
        binding.buttonAddChild.setOnClickListener(
            btnView -> {
                Intent intent = new Intent(getContext(), ChildFormActivity.class);
            startActivity(intent);
            });

        // Populate the list
        binding.childrenList.setAdapter(new ChildListAdapter(this, context));
        binding.childrenList.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

    class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder> {
        private ChildrenFragment childrenFragment;

        public ChildListAdapter(ChildrenFragment childrenFragment, Context context) {
            this.childrenFragment = childrenFragment;
            // TODO - These are example children to show the list.
            childrenFragment.manager.addChild("Hello","world");
            childrenFragment.manager.addChild("Sally","Smith");
            childrenFragment.manager.addChild("Brenden","Johnson");
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
        public ViewHolder onCreateViewHolder(
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
                ViewHolder holder,
            int position
        ) {
            // get child object from index
            Child child = childrenFragment.manager.getChildByIndex(position);

            // change the text to display childs name
            holder.titleCreationName.setText(child.getFirstName());

            // make the list item clickable
            // TODO - change to edit activity.
            holder.itemView.setOnClickListener((View view) -> {

            });
        }

        @Override
        public int getItemCount() {
            return childrenFragment.manager.getChildren().size();
        }
    }
}
