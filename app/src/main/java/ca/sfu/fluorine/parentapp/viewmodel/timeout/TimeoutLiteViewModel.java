package ca.sfu.fluorine.parentapp.viewmodel.timeout;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.store.TimeoutStorage;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TimeoutLiteViewModel extends ViewModel {
    private final TimeoutStorage storage;

    @Inject
    public TimeoutLiteViewModel(TimeoutStorage storage) {
        this.storage = storage;
    }

    public void initializeTimeout(long totalDuration) {
        storage.getEditor()
                .clear()
                .putLong(TimeoutStorage.TOTAL_DURATION, totalDuration)
                .putLong(TimeoutStorage.EXPIRED_TIME, System.currentTimeMillis() + totalDuration)
                .putLong(TimeoutStorage.REMAINING_TIME, totalDuration)
                .putBoolean(TimeoutStorage.IS_RUNNING, true)
                .putInt(TimeoutStorage.SPEED_PERCENTAGE, TimeoutStorage.NORMAL_PERCENTAGE)
                .apply();
    }

    public void clear() {
        storage.getEditor().clear().apply();
    }

    public boolean hasSavedTimer() {
        return storage.hasTimer();
    }
}
