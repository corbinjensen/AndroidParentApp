package ca.sfu.fluorine.parentapp.viewmodel.zen;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;


import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.store.BreathingStorage;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Represent a view model for zen screen (the whole taking breaths feature)
 */
@HiltViewModel
public class BreathingViewModel extends ViewModel {
    // Storage
    private final BreathingStorage storage;

    @Inject
    public BreathingViewModel(@NonNull BreathingStorage storage) {
        super();
        this.storage = storage;;
    }


    public int getBreathCount() {
        return storage.getBreathCount();
    }

    public void setBreathCount(int breathCount) {
        storage.storeBreathCount(breathCount);
    }
}
