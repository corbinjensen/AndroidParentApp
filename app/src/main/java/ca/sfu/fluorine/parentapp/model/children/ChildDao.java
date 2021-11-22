package ca.sfu.fluorine.parentapp.model.children;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

/**
 * Represents a data access object (DAO) to manipulate data about children in the database
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

	@Nullable
	@Query("SELECT * FROM children WHERE child_id = :id LIMIT 1")
	public abstract Child getChildById(int id);

	@Update
	public abstract void updateChild(Child child);

	@Query("SELECT child_id FROM children WHERE createdTime > :createdTime " +
			"ORDER BY createdTime LIMIT 1")
	abstract List<Integer> getFirstChildIdAfterCreationTime(long createdTime);

	@Query("SELECT child_id FROM children " +
			"ORDER BY createdTime LIMIT 1")
	abstract List<Integer> getFirstCreatedChildId();

	// Get the next child according to their creation time
	// if the last child's turn is done, then returns the first created child
	@Transaction
	public int getNextChildId(Child child) {
		List<Integer> ids = getFirstChildIdAfterCreationTime(child.getCreatedTime());
		if (ids.isEmpty()) {
			ids = getFirstCreatedChildId();
		}
		return (ids.isEmpty()) ? Child.getUnspecifiedChild().getId() : ids.get(0);
	}
}
