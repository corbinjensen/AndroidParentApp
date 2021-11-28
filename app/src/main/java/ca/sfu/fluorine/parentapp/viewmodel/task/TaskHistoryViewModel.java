package ca.sfu.fluorine.parentapp.viewmodel.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurnDao;

public class TaskHistoryViewModel extends AndroidViewModel {
    private final WhoseTurnDao whoseTurnDao;

    public TaskHistoryViewModel(@NonNull Application application) {
        super(application);
        whoseTurnDao = AppDatabase.getInstance(application).whoseTurnDao();
    }

    public LiveData<List<WhoseTurnRecord>> getTaskHistoryById(int taskId) {
        return whoseTurnDao.getAllWhoseTurnHistoryFrom(taskId);
    }
}
