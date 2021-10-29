package ca.sfu.fluorine.parentapp.model;

import android.content.Context;
import android.content.SharedPreferences;

public class TimeoutSetting {
	private static TimeoutSetting instance;
	private final static String KEY = "timeout";
	private final static String REMAIN = "remain";
	private final static String RUNNING = "running";
	private SharedPreferences preferences;
	private TimeoutSetting() {}

	public static TimeoutSetting getInstance(Context context) {
		if (instance == null) {
			instance = new TimeoutSetting();
		}
		instance.preferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
		return instance;
	}

	public void saveTimer(TimeoutTimer timer) {
		long millisLeft = timer.getRemainingTimeInMillis();
		if (timer.isFinished()) {
			preferences
					.edit()
					.putLong(REMAIN, millisLeft)
					.putBoolean(RUNNING, timer.isRunning()).apply();
		}

	}

	public Long getSavedRemainingMillis() {
		long value = preferences.getLong(REMAIN, -1);
		return (value > 0) ? value : null;
	}

	public boolean isTimerRunning() {
		return preferences.getBoolean(RUNNING, true);
	}

	public void clear() {
		preferences.edit().clear().apply();
	}
}
