package ca.sfu.fluorine.parentapp.viewmodel.timeout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ca.sfu.fluorine.parentapp.store.TimeoutStorage;

public class TimeoutLiteViewModel extends AndroidViewModel {
    private final TimeoutStorage storage;

    public TimeoutLiteViewModel(@NonNull Application application) {
        super(application);
        storage = TimeoutStorage.getInstance(application);
    }

    public void initializeTimeout(long totalDuration) {
        storage.getEditor()
                .clear()
                .putLong(TimeoutStorage.TOTAL_DURATION, totalDuration)
                .putLong(TimeoutStorage.EXPIRED_TIME, System.currentTimeMillis() + totalDuration)
                .putLong(TimeoutStorage.REMAINING_TIME, totalDuration)
                .putBoolean(TimeoutStorage.IS_RUNNING, true)
                .apply();
    }

    public void clear() {
        storage.getEditor().clear().apply();
    }

    public boolean hasSavedTimer() {
        return storage.hasTimer();
    }
}
