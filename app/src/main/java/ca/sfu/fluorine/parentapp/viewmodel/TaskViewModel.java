package ca.sfu.fluorine.parentapp.viewmodel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;
import ca.sfu.fluorine.parentapp.service.IconService;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TaskViewModel extends ViewModel {
    private final TaskDao taskDao;
    private final LiveData<List<TaskWithChild>> liveTasksWithChildren;
    private final IconService iconService;

    @Inject
    public TaskViewModel(@NonNull TaskDao taskDao, IconService iconService) {
        this.taskDao = taskDao;
        liveTasksWithChildren = taskDao.getAllTasksWithChildren();
        this.iconService = iconService;
    }

    public LiveData<List<TaskWithChild>> getLiveTasksWithChildren() {
        return liveTasksWithChildren;
    }

    public void addTask(Task task) {
        taskDao.add(task);
    }

    @Nullable
    public TaskWithChild getTaskByIdWithChild(int taskId) {
        return taskDao.getTaskByIdWithChild(taskId);
    }

    public void updateTask(Task task) {
        taskDao.update(task);
    }

    public void deleteTask(Task task) {
        taskDao.delete(task);
    }

    public IconService getIconService() {
        return iconService;
    }
}
