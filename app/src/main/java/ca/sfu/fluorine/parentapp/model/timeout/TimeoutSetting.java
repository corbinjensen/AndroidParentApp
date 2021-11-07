package ca.sfu.fluorine.parentapp.model.timeout;

import android.content.Context;
import android.content.SharedPreferences;

public class TimeoutSetting {
	private static TimeoutSetting instance;
	private final static String KEY = "timeout";
	private final static String RUNNING = "running";
	private final static String EXPIRED_TIME = "expired";
	private final static String REMAINING_TIME = "remaining";
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
		if (timer == null) return;
		if (timer.getState() != TimeoutTimer.TimerState.FINISHED) {
			preferences.edit()
					.putLong(REMAINING_TIME, timer.getMillisLeft())
					.putLong(EXPIRED_TIME, timer.expiredTime)
					.putBoolean(RUNNING, timer.getState() == TimeoutTimer.TimerState.RUNNING)
					.apply();
		}

	}

	public Long getExpiredTime() {
		long value = preferences.getLong(EXPIRED_TIME, -1);
		return (value > 0) ? value : null;
	}

	public long getRemainingTime() {
		return preferences.getLong(REMAINING_TIME, 0);
	}

	public boolean isTimerRunning() {
		return preferences.getBoolean(RUNNING, true);
	}

	public void clear() {
		preferences.edit().clear().apply();
	}

	public TimeoutTimer makeTimer() {
		Long expiredTime = getExpiredTime();
		if (expiredTime == null) return null;
		if (!isTimerRunning()) {
			long remainingTime = getRemainingTime();
			expiredTime = remainingTime + System.currentTimeMillis();
		}
		return new TimeoutTimer(expiredTime);
	}
}
