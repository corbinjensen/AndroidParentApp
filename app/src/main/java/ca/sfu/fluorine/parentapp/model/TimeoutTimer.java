package ca.sfu.fluorine.parentapp.model;

import android.icu.util.Calendar;
import android.os.CountDownTimer;

/**
 * TimeoutTimer.java
 *
 * Represents the timeout timer.
 */
public class TimeoutTimer {
	public final static long MINUTES_TO_MILLIS = 60000;
	private final static long INTERVAL = 1000;
	private TimerState state = TimerState.PAUSED;
	public final long expiredTime;
	private long millisLeft;
	private CountDownTimer timer;
	private Runnable actionOnTick, actionOnFinish;

	public enum TimerState {
		RUNNING,
		PAUSED,
		FINISHED
	}

	public TimeoutTimer(long expiredTime) {
		this.expiredTime = expiredTime;
		millisLeft = expiredTime - Calendar.getInstance().getTimeInMillis();
		if (millisLeft < 0) millisLeft = 0;
		timer = makeTimer(millisLeft);
	}

	public void registerActions(Runnable actionOnTick, Runnable actionOnFinish) {
		this.actionOnTick = actionOnTick;
		this.actionOnFinish = actionOnFinish;
	}

	public void toggle() {
		if (state == TimerState.PAUSED) {
			timer = makeTimer(millisLeft);
			timer.start();
			state = TimerState.RUNNING;
		} else {
			timer.cancel();
			state = TimerState.PAUSED;
		}
	}

	public void discard() {
		state = TimerState.FINISHED;
		if (timer == null) return;
		timer.cancel();
		timer = null;
	}

	public long getMillisLeft() {
		return millisLeft;
	}

	public TimerState getState() {
		return state;
	}

	private static void execute(Runnable action) {
		if (action == null) return;
		action.run();
	}

	private CountDownTimer makeTimer(long millis) {
		return new CountDownTimer(millis, INTERVAL) {
			@Override
			public void onTick(long millisUntilFinished) {
				millisLeft = millisUntilFinished;
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
