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

	@Query("SELECT children.*, completionTime FROM whose_turn " +
			"JOIN children ON child_id = assigned_child_id " +
			"JOIN tasks ON task_id = assigned_task_id " +
			"WHERE assigned_child_id = :taskId " +
			"ORDER BY completionTime DESC")
	public abstract List<WhoseTurnRecord> getAllWhoseTurnHistoryFrom(int taskId);
}
