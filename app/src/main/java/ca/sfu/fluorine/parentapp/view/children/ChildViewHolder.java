package ca.sfu.fluorine.parentapp.view.children;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;

public class ChildViewHolder extends RecyclerView.ViewHolder {
	TextView titleCreationName;
	CardView childCard;

	public ChildViewHolder(@NonNull View itemView) {
		super(itemView);

		// Find all the components of the view.
		titleCreationName = itemView.findViewById(R.id.childNameDisplay);
		childCard = itemView.findViewById(R.id.childCard);
	}
}
