package ca.sfu.fluorine.parentapp.model;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

public class TimeoutTimer {
	public final static long MINUTES_TO_MILLIS = 60000;
	private boolean pristine = true, running = false;
	private long remainingTimeInMillis;
	private CountDownTimer timer;
	private Runnable actionOnTick, actionOnFinish;

	public TimeoutTimer(long millis) {
		remainingTimeInMillis = millis;
		timer = makeTimer(remainingTimeInMillis);
	}

	public void registerActions(@NonNull Runnable actionOnTick, @NonNull Runnable actionOnFinish) {
		this.actionOnTick = actionOnTick;
		this.actionOnFinish = actionOnFinish;
	}

	public void toggle() {
		pristine = false;
		running = !running;
		if (running) {
			timer = makeTimer(remainingTimeInMillis);
			timer.start();
		} else {
			timer.cancel();
		}
	}

	public void discard() {
		if (timer == null) return;
		timer.cancel();
		timer = null;
	}

	public long getRemainingTimeInMillis() {
		return remainingTimeInMillis;
	}

	public boolean isPristine() {
		return pristine;
	}

	public boolean isRunning() {
		return running;
	}

	private static void execute(Runnable action) {
		if (action == null) return;
		action.run();
	}

	private CountDownTimer makeTimer(long timeInMillis) {
		return new CountDownTimer(timeInMillis, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				remainingTimeInMillis = millisUntilFinished;
				execute(actionOnTick);
			}

			@Override
			public void onFinish() {
				discard();
				execute(actionOnFinish);
			}
		};
	}
}
