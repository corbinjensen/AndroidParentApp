package ca.sfu.fluorine.parentapp.view.task;

import android.app.Service;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.service.IconService;
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

    public void populateData(TaskWithChild taskWithChild, Context context, IconService service) {
		taskName.setText(taskWithChild.getTask().getName());

		Child child = taskWithChild.getChild();
		if (child == null) {
			child = Child.getUnspecifiedChild();
			childPhotoTask.setVisibility(View.INVISIBLE);
		} else {
			childPhotoTask.setVisibility(View.VISIBLE);
			service.loadChildImageToView(child, childPhotoTask);
		}
		String childName = Utility.formatChildName(context, child);
		childNameTask.setText(context.getString(R.string.next_turn_info, childName));
    }
}
