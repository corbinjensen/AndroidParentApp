package ca.sfu.fluorine.parentapp.model.timeout;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;

public class TimeoutStorage {
    private final static String KEY = "timeout";
    public final static String IS_RUNNING = "running";
    public final static String EXPIRED_TIME = "expired";
    public final static String REMAINING_TIME = "remaining";
    public final static String TOTAL_DURATION = "duration";
    private final SharedPreferences preferences;

    public TimeoutStorage(@NonNull Application application) {
        preferences = application.getSharedPreferences(KEY, Application.MODE_PRIVATE);
    }

    public void clear() {
        preferences.edit().clear().apply();
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

    public long getTimerDuration() {
        return !isTimerRunning()
                ? getRemainingTime()
                : getExpiredTime() - System.currentTimeMillis();
    }
}
