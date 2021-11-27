package ca.sfu.fluorine.parentapp.store;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import javax.inject.Inject;

/**
 * Represents the storage for timeout timer parameters
 */
public class TimeoutStorage {
    private final static String KEY = "timeout_storage";
    public final static String IS_RUNNING = "running";
    public final static String EXPIRED_TIME = "expired";
    public final static String REMAINING_TIME = "remaining";
    public final static String TOTAL_DURATION = "duration";
    private final SharedPreferences preferences;

    @Inject
    public TimeoutStorage(@NonNull Application application) {
        preferences = application.getSharedPreferences(KEY, Application.MODE_PRIVATE);
    }

    public boolean isTimerRunning() {
        return preferences.getBoolean(IS_RUNNING, false);
    }

    public boolean hasTimer() {
        return getTotalDuration() != 0;
    }

    public long getRemainingTime() {
        return preferences.getLong(REMAINING_TIME, 0);
    }

    public long getExpiredTime() {
        return preferences.getLong(EXPIRED_TIME, System.currentTimeMillis());
    }

    public long getTotalDuration() {
        return preferences.getLong(TOTAL_DURATION, 0);
    }

    public SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    public long calculateRemainingMillis() {
        return !isTimerRunning()
                ? getRemainingTime()
                : getExpiredTime() - System.currentTimeMillis();
    }
}
