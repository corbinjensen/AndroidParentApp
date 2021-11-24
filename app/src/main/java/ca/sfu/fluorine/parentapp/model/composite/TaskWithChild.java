package ca.sfu.fluorine.parentapp.model.composite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.task.Task;

/**
 * Intermediate data class to represents one-to-one relationship
 * between Task and Child models
 *
 * Each task either associate with 0 or 1 child
 */
public class TaskWithChild {
	@Embedded Task task;
	@Embedded Child child;

	@NonNull
	public Task getTask() {
		return task;
	}

	@Nullable
	public Child getChild() {
		return child;
	}

	public void setChild(@Nullable Child child) {
		this.child = child;
	}

	public void setTask(@NonNull Task task) {
		this.task = task;
	}
}
