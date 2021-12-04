package ca.sfu.fluorine.parentapp.viewmodel.task;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurnDao;
import ca.sfu.fluorine.parentapp.service.IconService;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Represent a view model for task history screen
 */
@HiltViewModel
public class TaskHistoryViewModel extends ViewModel {
    private final WhoseTurnDao whoseTurnDao;
    private final IconService service;

    @Inject
    public TaskHistoryViewModel(WhoseTurnDao whoseTurnDao, IconService service) {
        this.whoseTurnDao = whoseTurnDao;
        this.service = service;
    }

    public LiveData<List<WhoseTurnRecord>> getTaskHistoryById(int taskId) {
        return whoseTurnDao.getAllWhoseTurnHistoryFrom(taskId);
    }

    public Bitmap getChildImageFromTaskHistory(@NonNull WhoseTurnRecord record) {
        return service.loadBitmapFrom(record.getChild());
    }
}
