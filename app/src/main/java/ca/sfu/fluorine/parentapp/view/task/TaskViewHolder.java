package ca.sfu.fluorine.parentapp.view.task;

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
import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

/**
 * TaskViewHolder.java - Represents the Task View being Held
 */
public class TaskViewHolder extends RecyclerView.ViewHolder {
	final TextView taskName;
	final TextView childNameTask;
	final ImageView childPhotoTask;

	public TaskViewHolder(@NonNull View itemView) {
		super(itemView);

		// Find all the components of the view.
        taskName = itemView.findViewById(R.id.taskName);
        childNameTask = itemView.findViewById(R.id.childNameTask);
        childPhotoTask = itemView.findViewById(R.id.childPhotoTask);

	}

    public void populateData(Context context, TaskWithChild taskWithChild, Bitmap bitmap) {
		taskName.setText(taskWithChild.getTask().getName());

		Child child = taskWithChild.getChild();
		if (child == null) {
			child = Child.getUnspecifiedChild();
		}
		String childName = Utility.formatChildName(context, child);
		childNameTask.setText(context.getString(R.string.next_turn_info, childName));
        Utility.setupImage(child, bitmap, childPhotoTask);

		itemView.setOnClickListener((View view) -> {
			Intent intent = EditTaskActivity.makeIntent(context, taskWithChild.getTask().getId());
			context.startActivity(intent);
		});
    }
}
