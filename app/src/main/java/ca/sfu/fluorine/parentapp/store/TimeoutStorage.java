package ca.sfu.fluorine.parentapp.store;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

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
    public static TimeoutStorage INSTANCE;

    private TimeoutStorage(@NonNull Application application) {
        preferences = application.getSharedPreferences(KEY, Application.MODE_PRIVATE);
    }

    public static TimeoutStorage getInstance(@NonNull Application application) {
        if (INSTANCE == null) {
            INSTANCE = new TimeoutStorage(application);
        }
        return INSTANCE;
    }

    public boolean isTimerRunning() {
        return preferences.getBoolean(IS_RUNNING, false);
    }

    public boolean hasTimer() {
        Log.d(null, "Duration: " + getTotalDuration());
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
        Log.d(null, "Remaining: " + getRemainingTime());
        Log.d(null, "Expired: " + getExpiredTime());
        return !isTimerRunning()
                ? getRemainingTime()
                : getExpiredTime() - System.currentTimeMillis();
    }
}
