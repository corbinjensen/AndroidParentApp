package ca.sfu.fluorine.parentapp.viewmodel.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;

public class TaskEditViewModel extends AndroidViewModel {
    private final TaskDao taskDao;

    public TaskEditViewModel(@NonNull Application application) {
        super(application);
        taskDao =  AppDatabase.getInstance(application).taskDao();
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
