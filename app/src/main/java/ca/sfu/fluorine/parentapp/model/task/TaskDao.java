package ca.sfu.fluorine.parentapp.model.task;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Represents a data access object (DAO) to manipulate task data in the database
 */
@Dao
public abstract class TaskDao {
	// Either return an empty list or 1-element list
	@Query("SELECT * FROM tasks LEFT JOIN children " +
			"ON child_turn_id == child_id " +
			"WHERE task_id = :taskId LIMIT 1")
	public abstract TaskAndChild getTaskByIdWithChild(int taskId);

	@Insert
	public abstract void addTask(Task task);

	@Update
	public abstract void updateTask(Task task);

	@Delete
	public abstract void deleteTask(Task task);


	@Query("SELECT * FROM tasks LEFT JOIN children ON child_turn_id == child_id")
	public abstract List<TaskAndChild> getAllTasksWithChildren();
}
