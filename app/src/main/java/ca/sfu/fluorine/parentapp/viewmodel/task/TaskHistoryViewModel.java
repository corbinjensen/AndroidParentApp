package ca.sfu.fluorine.parentapp.viewmodel.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurnDao;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TaskHistoryViewModel extends ViewModel {
    private final WhoseTurnDao whoseTurnDao;

    @Inject
    public TaskHistoryViewModel(WhoseTurnDao whoseTurnDao) {
        this.whoseTurnDao = whoseTurnDao;
    }

    public LiveData<List<WhoseTurnRecord>> getTaskHistoryById(int taskId) {
        return whoseTurnDao.getAllWhoseTurnHistoryFrom(taskId);
    }
}
