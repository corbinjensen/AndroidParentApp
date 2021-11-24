package ca.sfu.fluorine.parentapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;

public class TaskViewModel extends AndroidViewModel {
    private final TaskDao taskDao;
    private final ChildDao childDao;

    private final LiveData<List<TaskWithChild>> liveTasksWithChildren;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        taskDao = appDatabase.taskDao();
        childDao = appDatabase.childDao();
        liveTasksWithChildren = taskDao.getAllTasksWithChildren();
    }

    public LiveData<List<TaskWithChild>> getLiveTasksWithChildren() {
        return liveTasksWithChildren;
    }

    public void addTask(Task task) {
        taskDao.addTask(task);
    }

    @Nullable
    public TaskWithChild getTaskByIdWithChild(int taskId) {
        return taskDao.getTaskByIdWithChild(taskId);
    }

    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }
}
