package ca.sfu.fluorine.parentapp.viewmodel.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurn;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurnDao;

public class TaskEditViewModel extends AndroidViewModel {
    private final TaskDao taskDao;
    private final WhoseTurnDao whoseTurnDao;

    public TaskEditViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        taskDao = appDatabase.taskDao();
        whoseTurnDao = appDatabase.whoseTurnDao();
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

    public void addChildrenToTaskHistory(int taskId, int childrenId) {
        WhoseTurn turn = new WhoseTurn(taskId, childrenId);
        whoseTurnDao.add(turn);
    }
}
