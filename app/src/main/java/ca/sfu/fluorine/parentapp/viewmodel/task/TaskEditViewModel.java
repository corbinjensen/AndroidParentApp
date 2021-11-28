package ca.sfu.fluorine.parentapp.viewmodel.task;


import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.composite.TaskWithChild;
import ca.sfu.fluorine.parentapp.model.task.Task;
import ca.sfu.fluorine.parentapp.model.task.TaskDao;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurnDao;
import dagger.hilt.android.lifecycle.HiltViewModel;
import ca.sfu.fluorine.parentapp.model.task.WhoseTurn;

@HiltViewModel
public class TaskEditViewModel extends ViewModel {
    private final TaskDao taskDao;
    private final WhoseTurnDao whoseTurnDao;

    @Inject
    public TaskEditViewModel(TaskDao taskDao, WhoseTurnDao whoseTurnDao) {
        this.taskDao = taskDao;
        this.whoseTurnDao = whoseTurnDao;
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
