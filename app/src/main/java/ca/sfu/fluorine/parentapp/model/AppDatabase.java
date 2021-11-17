package ca.sfu.fluorine.parentapp.model;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultDao;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;

/**
 * Represents the whole database of the app
 *
 * This database object only has one instance for the whole application
 */
@Database(entities = {Child.class, CoinResult.class, Task.class},
		version = 1,
		exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	private static final String DATABASE_NAME = "app_database";
	private static AppDatabase INSTANCE;

	// Data access objects
	public abstract ChildDao childDao();
	public abstract CoinResultDao coinResultDao();
	public abstract TaskDao taskDao();

	public static AppDatabase getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
					.allowMainThreadQueries() // Allow database to run on the UI thread
					.build();
		}
		return INSTANCE;
	}
}
