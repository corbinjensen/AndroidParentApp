package ca.sfu.fluorine.parentapp.model.task;

import androidx.room.Embedded;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Intermediate data class to represents one-to-one relationship
 * between Task and Child models
 */
public class TaskWithChild {
	@Embedded Task task;
	@Embedded Child child;

	public Task getTask() {
		return task;
	}

	public Child getChild() {
		return child;
	}
}
