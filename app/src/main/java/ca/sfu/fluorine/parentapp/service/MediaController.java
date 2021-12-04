package ca.sfu.fluorine.parentapp.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RawRes;

/**
 * Represent a media controller hosting inside an activity or fragment
 *
 * This controller can play sound or make the phone vibrate
 */
public class MediaController {
	private final MediaPlayer mediaPlayer;
	private final Vibrator vibrator;

	public MediaController(Context context, @RawRes int rawId) {
		mediaPlayer = MediaPlayer.create(context, rawId);
		mediaPlayer.setLooping(true);

		vibrator = context.getSystemService(Vibrator.class);
	}

	public void playSound() {
		mediaPlayer.start();
	}

	public void vibrate() {
		vibrator.vibrate(
				VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
		);
	}

	public void cancelAll() {
		mediaPlayer.pause();
		vibrator.cancel();
	}
}
