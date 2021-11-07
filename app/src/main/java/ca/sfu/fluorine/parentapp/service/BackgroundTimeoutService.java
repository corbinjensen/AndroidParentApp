package ca.sfu.fluorine.parentapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.NonNull;

import ca.sfu.fluorine.parentapp.model.TimeoutTimer;

public class BackgroundTimeoutService {
	public static void setAlarm(Context context, @NonNull TimeoutTimer timeoutTimer) {
		PendingIntent intent = TimeoutExpiredReceiver.makePendingIntent(context);
		context.getSystemService(AlarmManager.class)
				.setExact(AlarmManager.RTC_WAKEUP, timeoutTimer.expiredTime, intent);
	}

	public static void removeAlarm(Context context) {
		PendingIntent intent = TimeoutExpiredReceiver.makePendingIntent(context);
		context.getSystemService(AlarmManager.class).cancel(intent);
	}
}
