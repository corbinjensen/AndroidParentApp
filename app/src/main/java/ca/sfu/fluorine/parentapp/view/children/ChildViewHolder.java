package ca.sfu.fluorine.parentapp.view.children;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

public class ChildViewHolder extends RecyclerView.ViewHolder {
    final TextView titleCreationName;
    final ImageView childImage;

    public ChildViewHolder(@NonNull View itemView) {
        super(itemView);

        // Find all the components of the view.
        childImage = itemView.findViewById(R.id.childPhoto);
        titleCreationName = itemView.findViewById(R.id.childNameDisplay);
    }

    public void populateData(Context context, Child child, @Nullable Bitmap bitmap) {
        // change the text to display child name
        titleCreationName.setText(Utility.formatChildName(context, child));

        //Change display image of child
        Utility.setupImage(child, bitmap, childImage);

        // make the list item clickable
        itemView.setOnClickListener((View view) -> {
            Intent intent = EditChildActivity.makeIntent(context, child.getId());
            context.startActivity(intent);
        });
    }
}
