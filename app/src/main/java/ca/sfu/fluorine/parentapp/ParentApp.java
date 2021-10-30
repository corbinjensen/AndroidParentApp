package ca.sfu.fluorine.parentapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ca.sfu.fluorine.parentapp.service.TimeoutExpiredNotification;

public class ParentApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@SuppressLint("SourceLockedOrientationActivity")
			@Override
			public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
				// Lock the orientation of the application
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}

			@Override
			public void onActivityStarted(@NonNull Activity activity) { }

			@Override
			public void onActivityResumed(@NonNull Activity activity) { }

			@Override
			public void onActivityPaused(@NonNull Activity activity) { }

			@Override
			public void onActivityStopped(@NonNull Activity activity) { }

			@Override
			public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) { }

			@Override
			public void onActivityDestroyed(@NonNull Activity activity) { }
		});

		createTimeoutNotificationChannel();
	}


	private void createTimeoutNotificationChannel() {
		NotificationChannel channel = new NotificationChannel(
				TimeoutExpiredNotification.CHANNEL_ID,
				TimeoutExpiredNotification.CHANNEL_NAME,
				NotificationManager.IMPORTANCE_HIGH
		);
		NotificationManager manager = getSystemService(NotificationManager.class);
		manager.createNotificationChannel(channel);
	}
}
