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

	@Query("SELECT * FROM children ORDER BY lastCoinFlip DESC")
	public abstract List<Child> getAllChildrenOrderByRecentCoinFlips();

	@Query("SELECT * FROM children WHERE child_id = :id LIMIT 1")
	public abstract List<Child> getChildById(int id);

	@Update
	public abstract void updateChild(Child child);

	@Query("UPDATE children SET lastCoinFlip = :coinFlipTime WHERE child_id = :id")
	abstract void updateLastCoinFlipById(int id, long coinFlipTime);

	public void updateChildLastCoinFlip(int id) {
		updateLastCoinFlipById(id, System.currentTimeMillis());
	}
}
