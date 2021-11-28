package ca.sfu.fluorine.parentapp.model.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.BaseDao;
import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;

@Dao
public abstract class WhoseTurnDao implements BaseDao<WhoseTurn> {
    @Query("SELECT children.*, completionTime FROM whose_turn " +
            "JOIN children ON child_id = assigned_child_id " +
            "JOIN tasks ON task_id = assigned_task_id " +
            "WHERE assigned_task_id = :taskId " +
            "ORDER BY completionTime DESC")
    public abstract LiveData<List<WhoseTurnRecord>> getAllWhoseTurnHistoryFrom(int taskId);
}
