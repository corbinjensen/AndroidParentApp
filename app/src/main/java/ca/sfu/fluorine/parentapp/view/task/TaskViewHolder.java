package ca.sfu.fluorine.parentapp.view.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.TaskAndChild;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

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

    public void populateData(Context context, TaskAndChild taskAndChild) {
		taskName.setText(taskAndChild.getTask().getName());

		Child child = taskAndChild.getChild();
		if (child == null) {
			child = Child.getUnspecifiedChild();
		}
		String childName = Utility.formatChildName(context, child);
		childNameTask.setText(context.getString(R.string.next_turn_info, childName));
        Utility.setupImage(context, childPhotoTask, child);
    }
}
