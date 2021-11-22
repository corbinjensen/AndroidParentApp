package ca.sfu.fluorine.parentapp.view.children;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

public class ChildViewHolder extends RecyclerView.ViewHolder {
    TextView titleCreationName;
    ImageView childImage;

    public ChildViewHolder(@NonNull View itemView) {
        super(itemView);

        // Find all the components of the view.
        childImage = itemView.findViewById(R.id.childPhoto);
        titleCreationName = itemView.findViewById(R.id.childNameDisplay);
    }

    public void populateData(Context context, Child child) {
        // change the text to display child name
        titleCreationName.setText(Utility.formatChildName(context, child));

        //Change display image of child
        Utility.setupImage(context, childImage, child);

        // make the list item clickable
        itemView.setOnClickListener((View view) -> {
            Intent intent = EditChildActivity.makeIntent(context, child.getId());
            context.startActivity(intent);
        });
    }
}
