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
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TaskViewModel extends ViewModel {
    private final TaskDao taskDao;

    private final LiveData<List<TaskWithChild>> liveTasksWithChildren;

    @Inject
    public TaskViewModel(@NonNull TaskDao taskDao) {
        this.taskDao = taskDao;
        liveTasksWithChildren = taskDao.getAllTasksWithChildren();
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
}
