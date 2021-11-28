package ca.sfu.fluorine.parentapp.viewmodel.task;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.service.IconService;
import ca.sfu.fluorine.parentapp.store.ImageInternalStorage;

public class TaskListingViewModel extends AndroidViewModel {
    private final IconService service;

    private final LiveData<List<TaskWithChild>> liveTasksWithChildren;

    public TaskListingViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        liveTasksWithChildren = appDatabase.taskDao().getAllTasksWithChildren();
        service = new IconService(ImageInternalStorage.getInstance(application));
    }

    public LiveData<List<TaskWithChild>> getLiveTasksWithChildren() {
        return liveTasksWithChildren;
    }

    public Bitmap loadCurrentChildImage(TaskWithChild taskWithChild) {
        return service.loadBitmapFrom(taskWithChild.getChild());
    }
}
