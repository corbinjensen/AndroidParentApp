package ca.sfu.fluorine.parentapp.viewmodel.task;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;
import ca.sfu.fluorine.parentapp.service.IconService;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Represent a view model for task listing screen
 */
@HiltViewModel
public class TaskListingViewModel extends ViewModel {
    private final IconService service;
    private final LiveData<List<TaskWithChild>> liveTasksWithChildren;

    @Inject
    public TaskListingViewModel(TaskDao taskDao, IconService service) {
        liveTasksWithChildren = taskDao.getAllTasksWithChildren();
        this.service = service;
    }

    public LiveData<List<TaskWithChild>> getLiveTasksWithChildren() {
        return liveTasksWithChildren;
    }

    public Bitmap loadCurrentChildImage(TaskWithChild taskWithChild) {
        return service.loadBitmapFrom(taskWithChild.getChild());
    }
}
