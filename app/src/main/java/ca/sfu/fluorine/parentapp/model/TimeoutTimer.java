package ca.sfu.fluorine.parentapp.model;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

public class TimeoutTimer {
	private boolean pristine = true, running = false;
	private final int minutes;
	private long remainingTimeInMillis;
	private CountDownTimer timer;
	private Runnable actionOnUpdate, actionOnReset;

	public TimeoutTimer(int minutes) {
		this.minutes = minutes;
		reset();
	}

	public void registerAction(@NonNull Runnable actionOnUpdate,
							   @NonNull Runnable actionOnReset) {
		this.actionOnUpdate = actionOnUpdate;
		this.actionOnReset = actionOnReset;
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

	public void reset() {
		pristine = true;
		running = false;
		if (timer != null) timer.cancel();
		remainingTimeInMillis = minutes * 60000L;
		timer = makeTimer(remainingTimeInMillis);
		execute(actionOnReset);
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
				execute(actionOnUpdate);
			}

			@Override
			public void onFinish() {
				reset();
			}
		};
	}
}
