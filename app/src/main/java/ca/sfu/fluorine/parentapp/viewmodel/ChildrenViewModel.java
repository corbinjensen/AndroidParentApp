package ca.sfu.fluorine.parentapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;

public class ChildrenViewModel extends AndroidViewModel {
    private final ChildDao childDao;
    private final LiveData<List<Child>> childrenLiveData;
    private final LiveData<List<Child>> childrenByCoinFlipsLiveData;

    public ChildrenViewModel(@NonNull Application application) {
        super(application);
        childDao = AppDatabase.getInstance(application).childDao();
        childrenLiveData = childDao.getAllChildren();
        childrenByCoinFlipsLiveData = childDao.getAllChildrenOrderByRecentCoinFlips();
    }

    public LiveData<List<Child>> getChildrenLiveData() {
        return childrenLiveData;
    }

    public LiveData<List<Child>> getChildrenByCoinFlipsLiveData() {
        return childrenByCoinFlipsLiveData;
    }

    @Nullable
    public Child getChildById(int id) {
        return childDao.getChildById(id);
    }

    public void updateChild(Child child) {
        childDao.updateChild(child);
    }

    public void addChild(Child child) {
        childDao.addChild(child);
    }

    public void deleteChild(Child child) {
        childDao.deleteChild(child);
    }
}
