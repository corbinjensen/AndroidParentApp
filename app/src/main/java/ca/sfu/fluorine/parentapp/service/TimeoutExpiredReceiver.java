package ca.sfu.fluorine.parentapp.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * TimeoutExpiredReceiver.java
 *
 * Extends BroadcastReceiver, receives when
 * the timeout timer expires.
 */
public class TimeoutExpiredReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		TimeoutExpiredNotification.showNotification(context);
	}

	public static PendingIntent makePendingIntent(Context context) {
		Intent intent = new Intent(context, TimeoutExpiredReceiver.class);
		return PendingIntent.getBroadcast(
				context,
				0,
				intent,
				PendingIntent.FLAG_IMMUTABLE);
	}
}
