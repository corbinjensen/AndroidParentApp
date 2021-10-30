package ca.sfu.fluorine.parentapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import ca.sfu.fluorine.parentapp.R;

public class TimeoutExpiredNotification {
	public final static String CHANNEL_ID = "TimeoutExpiredNotification";
	public final static String CHANNEL_NAME = "Timer end";
	public final static int NOTIFICATION_ID = 0;

	public static void showNotification(Context context) {
		Notification notification = new Notification
				.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_stopwatch_solid)
				.setContentTitle("Timeout is finished")
				.setContentText("Time out")
				.build();
		context.getSystemService(NotificationManager.class)
				.notify(NOTIFICATION_ID, notification);
	}

	public static void hideNotification(Context context) {
		context.getSystemService(NotificationManager.class)
				.cancel(NOTIFICATION_ID);
	}
}
