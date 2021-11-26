package ca.sfu.fluorine.parentapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.timeout.TimeoutSetting;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TimeoutViewModel extends ViewModel {
    private final TimeoutSetting setting;

    @Inject
    public TimeoutViewModel(@NonNull TimeoutSetting setting) {
        this.setting = setting;
    }

    public TimeoutSetting getSetting() {
        return setting;
    }
}
