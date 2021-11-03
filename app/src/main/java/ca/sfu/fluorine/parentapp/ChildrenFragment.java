package ca.sfu.fluorine.parentapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.model.Child;
import ca.sfu.fluorine.parentapp.model.ChildrenManager;

public class ChildrenFragment extends Fragment {

    private final ChildListAdapter adapter = new ChildListAdapter(getContext());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecyclerView childList = getView().findViewById(R.id.childrenList);
        childList.setAdapter(adapter);
        childList.setLayoutManager(new LinearLayoutManager(getContext()));

        return super.onCreateView(inflater, container, savedInstanceState);
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
