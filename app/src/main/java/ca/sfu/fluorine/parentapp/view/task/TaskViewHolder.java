package ca.sfu.fluorine.parentapp.view.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.task.TaskWithChild;
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

    public void populateData(Context context, TaskWithChild taskWithChild) {
        if(taskWithChild.getChild() == null){
        	childNameTask.setText(R.string.nullChildForTask);
		}else {
			childNameTask.setText(taskWithChild.getChild().getFirstName());
		}

        taskName.setText(taskWithChild.getTask().getName());

	    Bitmap childTaskPhoto = ImageInternalStorage.getInstance(context)
            .loadImage(taskWithChild.getChild().getPhotoFileName());
	    if(childTaskPhoto != null) {
            childPhotoTask.setImageBitmap(childTaskPhoto);
        }
    }
}
