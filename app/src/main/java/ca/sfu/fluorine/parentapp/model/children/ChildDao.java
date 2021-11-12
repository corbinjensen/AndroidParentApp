package ca.sfu.fluorine.parentapp.model.children;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Represents data access point for children data
 */
@Dao
public abstract class ChildDao {
	@Insert
	public abstract void addChild(Child child);

	@Delete
	public abstract void deleteChild(Child child);

	@Query("SELECT * FROM children ORDER BY createdTime DESC")
	public abstract List<Child> getAllChildren();

	// By the magic of SQL...
	@Query("SELECT children.* FROM children LEFT JOIN coin_result " +
			"ON child_id == selected_child_id " +
			"GROUP BY child_id ORDER BY MAX(coin_result.dateTimeOfFlip)")
	public abstract List<Child> getAllChildrenOrderByRecentCoinFlips();

	@Query("SELECT * FROM children WHERE child_id = :id LIMIT 1")
	public abstract List<Child> getChildById(int id);

	@Update
	public abstract void updateChild(Child child);
}
