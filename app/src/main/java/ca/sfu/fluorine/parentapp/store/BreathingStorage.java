package ca.sfu.fluorine.parentapp.store;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

/**
 * Stores the breath count selection from the Zen Activity
 */
@ActivityRetainedScoped
public class BreathingStorage {
    private static final String KEY = "breathing";
    private static final String BREATH_COUNT = "breath_count";
    private static final int DEFAULT_BREATH_COUNT = 5;

    private final SharedPreferences preferences;

    @Inject
    public BreathingStorage(@NonNull Application application) {
        preferences = application
                .getApplicationContext()
                .getSharedPreferences(KEY, Application.MODE_PRIVATE);
    }

    public void storeBreathCount(int breathCount) {
        preferences.edit().putInt(BREATH_COUNT, breathCount).apply();
    }

    public int getBreathCount() {
        return preferences.getInt(BREATH_COUNT, DEFAULT_BREATH_COUNT);
    }
}
