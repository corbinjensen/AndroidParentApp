package ca.sfu.fluorine.parentapp.view.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.task.TaskAndChild;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;

/**
 * TaskViewHolder.java - Represents the Task View being Held
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {
	TextView taskName;
	TextView childNameTask;
	ImageView childPhotoTask;

	public TaskViewHolder(@NonNull View itemView) {
		super(itemView);

		// Find all the components of the view.
        taskName = itemView.findViewById(R.id.taskName);
        childNameTask = itemView.findViewById(R.id.childNameTask);
        childPhotoTask = itemView.findViewById(R.id.childPhotoTask);

	}

    public void populateData(Context appContext, TaskAndChild taskAndChild) {
        childNameTask.setText(taskAndChild.getChild().getFirstName());
	    taskName.setText(taskAndChild.getTask().getName());

	    Bitmap childTaskPhoto = ImageInternalStorage.getInstance(appContext)
            .loadImage(taskAndChild.getChild().getPhotoFileName());
	    if(childTaskPhoto != null) {
            childPhotoTask.setImageBitmap(childTaskPhoto);
        }
    }
}
