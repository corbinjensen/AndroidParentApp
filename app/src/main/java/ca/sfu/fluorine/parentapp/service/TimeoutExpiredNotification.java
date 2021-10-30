package ca.sfu.fluorine.parentapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ca.sfu.fluorine.parentapp.MainActivity;
import ca.sfu.fluorine.parentapp.R;

public class TimeoutExpiredNotification {
	public final static String CHANNEL_ID = "TimeoutExpiredNotification";
	public final static String CHANNEL_NAME = "Timer end";
	public final static int NOTIFICATION_ID = 0;

	public static void showNotification(Context context) {
		Intent actionIntent = new Intent(Intent.ACTION_SCREEN_ON);
		PendingIntent pendingIntent =
				PendingIntent.getActivity(context, 0, actionIntent,
						PendingIntent.FLAG_IMMUTABLE);

		Notification notification = new NotificationCompat
				.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_stopwatch_solid)
				.setContentTitle("Timeout is finished")
				.setContentText("Time out")
				.setPriority(NotificationCompat.PRIORITY_HIGH)
				.setCategory(NotificationCompat.CATEGORY_MESSAGE)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.build();
		NotificationManagerCompat.from(context)
				.notify(NOTIFICATION_ID, notification);
	}

	public static void hideNotification(Context context) {
		NotificationManagerCompat.from(context)
				.cancel(NOTIFICATION_ID);
	}

	public static NotificationChannel makeNotificationChannel() {
		return new NotificationChannel(
				CHANNEL_ID,
				CHANNEL_NAME,
				NotificationManager.IMPORTANCE_HIGH);
	}
}
