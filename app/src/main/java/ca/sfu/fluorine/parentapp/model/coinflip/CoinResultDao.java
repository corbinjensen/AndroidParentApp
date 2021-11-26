package ca.sfu.fluorine.parentapp.model.coinflip;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.composite.CoinResultWithChild;
import ca.sfu.fluorine.parentapp.model.BaseDao;

/**
 * Represents a data access object (DAO) to manipulate data about coin results in the database
 */
@Dao
public abstract class CoinResultDao implements BaseDao<CoinResult> {
	@Transaction
	@Query("SELECT * FROM coin_result JOIN children" +
			" ON selected_child_id == children.child_id ORDER BY dateTimeOfFlip DESC")
	public abstract LiveData<List<CoinResultWithChild>> getAllCoinResultsWithChildren();
}
