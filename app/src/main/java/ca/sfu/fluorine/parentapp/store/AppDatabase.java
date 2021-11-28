package ca.sfu.fluorine.parentapp.store;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultDao;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurn;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurnDao;

/**
 * Represents the whole database of the app
 *
 * This database object only has one instance for the whole application
 */
@Database(entities = {Child.class, CoinResult.class, Task.class, WhoseTurn.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
	public static final String DATABASE_NAME = "app_database";

	// Data access objects
	public abstract ChildDao childDao();
	public abstract CoinResultDao coinResultDao();
	public abstract TaskDao taskDao();
	public abstract WhoseTurnDao whoseTurnDao();
}
