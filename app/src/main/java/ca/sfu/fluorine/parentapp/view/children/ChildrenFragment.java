package ca.sfu.fluorine.parentapp.view.children;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentChildrenBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * ChildrenFragment.java - represents the UI of the configure children feature.
 */
public class ChildrenFragment extends Fragment {
    private AppDatabase database;
	private FragmentChildrenBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getInstance(requireContext().getApplicationContext());
    }

    @Override
	public View onCreateView(
	    @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
		// Inflate the layout for this fragment
		binding = FragmentChildrenBinding.inflate(
		    inflater,
            container,
            false
        );
		return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // floating action button
        binding.buttonAddChild.setOnClickListener(
                btnView -> {
                    Intent intent = new Intent(requireContext(), AddChildActivity.class);
                    startActivity(intent);
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetch from database
        List<Child> children = database.childDao().getAllChildren();
        if (children.isEmpty()) {
            binding.childrenList.showEmpty();
        } else {
            binding.childrenList.useAdapter(new ChildListAdapter(children));
        }
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

    class ChildListAdapter extends RecyclerView.Adapter<ChildViewHolder> {
        private final List<Child> children;

        public ChildListAdapter(List<Child> children) {
            this.children = children;
        }

        @NonNull
        @Override
        public ChildViewHolder onCreateViewHolder(
            @NonNull
                ViewGroup parent,
            int viewType
        ) {
            View view = LayoutInflater.from(requireContext()).inflate(
                R.layout.child_row_layout,
                parent,
                false
            );
            return new ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(
            @NonNull
                    ChildViewHolder holder,
            int position
        ) {
            // get child object from index
            Child child = children.get(position);
            holder.populateData(requireContext(), child);
        }

        @Override
        public int getItemCount() {
            return children.size();
        }
    }
}
