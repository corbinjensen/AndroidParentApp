package ca.sfu.fluorine.parentapp.model.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Intermediate data class to represents one-to-one relationship
 * between Task and Child models
 *
 * Each task either associate with 0 or 1 child
 */
public class TaskAndChild {
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
}
