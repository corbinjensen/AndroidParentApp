package ca.sfu.fluorine.parentapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ca.sfu.fluorine.parentapp.R;

public class TimeoutExpiredNotification {
	public final static String CHANNEL_ID = "TimeoutExpiredNotification";
	public final static int NOTIFICATION_ID = 0;

	public static void showNotification(Context context) {
		Intent actionIntent = new Intent(Intent.ACTION_SCREEN_ON);
		PendingIntent pendingIntent =
				PendingIntent.getActivity(context, 0, actionIntent,
						PendingIntent.FLAG_IMMUTABLE);

		Notification notification = new NotificationCompat
				.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_stopwatch_solid)
				.setContentTitle(context.getString(R.string.timeout_notification_title))
				.setContentText(context.getString(R.string.timeout_notification_content))
				.setPriority(NotificationCompat.PRIORITY_HIGH)
				.setCategory(NotificationCompat.CATEGORY_CALL)
				.setShowWhen(false)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.build();
		notification.flags |= Notification.FLAG_INSISTENT;
		NotificationManagerCompat.from(context)
				.notify(NOTIFICATION_ID, notification);
	}

	public static void hideNotification(Context context) {
		NotificationManagerCompat.from(context)
				.cancel(NOTIFICATION_ID);
	}

	public static NotificationChannel makeNotificationChannel(Context context) {
		NotificationChannel channel = new NotificationChannel(
				CHANNEL_ID,
				context.getString(R.string.timeout_notification_channel_name),
				NotificationManager.IMPORTANCE_HIGH
		);
		Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		channel.setDescription(
				context.getString(R.string.timeout_notification_channel_description));
		channel.enableVibration(true);
		channel.setSound(sound, null);
		return channel;
	}
}
