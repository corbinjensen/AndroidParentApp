package ca.sfu.fluorine.parentapp.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import javax.inject.Singleton;

import ca.sfu.fluorine.parentapp.model.task.WhoseTurnDao;
import ca.sfu.fluorine.parentapp.store.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultDao;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Provides database instance and all data access objects for the whole application
 */
@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Singleton
    @Provides
    public AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(
                application.getApplicationContext(),
                AppDatabase.class,
                AppDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration() // Wipe the database when changing schemas
                .allowMainThreadQueries() // Allow database to run on the UI thread
                .build();
    }

    @Singleton
    @Provides
    public ChildDao provideChildDao(@NonNull AppDatabase database) {
        return database.childDao();
    }

    @Singleton
    @Provides
    public CoinResultDao provideCoinResultDao(@NonNull AppDatabase database) {
        return database.coinResultDao();
    }

    @Singleton
    @Provides
    public TaskDao provideTaskDao(@NonNull AppDatabase database) {
        return database.taskDao();
    }

    @Singleton
    @Provides
    public WhoseTurnDao provideWhoseTurnDao(@NonNull AppDatabase database) {
        return database.whoseTurnDao();
    }
}
