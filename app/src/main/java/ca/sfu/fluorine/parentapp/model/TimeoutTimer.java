package ca.sfu.fluorine.parentapp.model;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

public class TimeoutTimer {
	private boolean pristine = true, running = false;
	private final int minutes = 1;
	private long remainingTimeInMillis;
	private CountDownTimer timer;
	private Runnable actionOnUpdate;

	public TimeoutTimer() {
		reset();
	}

	public void registerAction(@NonNull Runnable action) {
		actionOnUpdate = action;
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
		remainingTimeInMillis = minutes * 60000;
		timer = makeTimer(minutes * 60000);
		runAction();
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

	private void runAction() {
		if (actionOnUpdate != null) {
			actionOnUpdate.run();
		}
	}

	private CountDownTimer makeTimer(long timeInMillis) {
		return new CountDownTimer(timeInMillis, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				remainingTimeInMillis = millisUntilFinished;
				runAction();
			}

			@Override
			public void onFinish() {
				running = false;
				runAction();
			}
		};
	}
}
