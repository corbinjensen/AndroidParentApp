package ca.sfu.fluorine.parentapp.service;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;

public class BackgroundTimeoutService {
	private final Context context;

	public BackgroundTimeoutService(Application application) {
		context = application.getApplicationContext();
	}

	public void setAlarmAt(long expiredTime) {
		PendingIntent intent = TimeoutExpiredReceiver.makePendingIntent(context);
		context.getSystemService(AlarmManager.class)
				.setExact(AlarmManager.RTC_WAKEUP, expiredTime, intent);
	}

	public void removeAlarm() {
		PendingIntent intent = TimeoutExpiredReceiver.makePendingIntent(context);
		context.getSystemService(AlarmManager.class).cancel(intent);
	}
}
