package ca.sfu.fluorine.parentapp.view.children;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;

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
        titleCreationName.setText(child.getFirstName());

        //Change display image of child
        ImageInternalStorage imageStorage = ImageInternalStorage.getInstance(context);
        Bitmap bitmapImage = imageStorage.loadImage(child.getPhotoFileName());
        if (bitmapImage != null) {
            childImage.setImageBitmap(imageStorage.loadImage(child.getPhotoFileName()));
        }

        // make the list item clickable
        itemView.setOnClickListener((View view) -> {
            Intent intent = ChildFormActivity.makeIntent(context, child.getId());
            context.startActivity(intent);
        });
    }
}
