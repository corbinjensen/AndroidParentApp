package ca.sfu.fluorine.parentapp.model.task;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;
import ca.sfu.fluorine.parentapp.model.BaseDao;

/**
 * Represents a data access object (DAO) to manipulate task data in the database
 */
@Dao
public abstract class TaskDao implements BaseDao<Task> {
	// Either return an empty list or 1-element list
	@Nullable
	@Query("SELECT * FROM tasks LEFT JOIN children " +
			"ON child_turn_id == child_id " +
			"WHERE task_id = :taskId LIMIT 1")
	public abstract TaskWithChild getTaskByIdWithChild(int taskId);

	@Query("SELECT * FROM tasks LEFT JOIN children ON child_turn_id == child_id")
	public abstract LiveData<List<TaskWithChild>> getAllTasksWithChildren();
}
