package ca.sfu.fluorine.parentapp.model.coinflip;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class CoinResultDao {
	@Transaction
	@Query("SELECT * FROM coin_result JOIN children" +
			" ON selected_child_id == children.child_id ORDER BY dateTimeOfFlip DESC")
	public abstract List<CoinResultAndChild> getAllCoinResultsWithChildren();

	@Insert
	public abstract void addNewCoinResult(CoinResult coinResult);
}
