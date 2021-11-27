package ca.sfu.fluorine.parentapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ca.sfu.fluorine.parentapp.model.timeout.TimeoutSetting;

public class TimeoutViewModel extends AndroidViewModel {
    private final TimeoutSetting setting;

    public TimeoutViewModel(@NonNull Application application) {
        super(application);
        setting = TimeoutSetting.getInstance(application);
    }

    public TimeoutSetting getSetting() {
        return setting;
    }
}
