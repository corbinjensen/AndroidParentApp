package ca.sfu.fluorine.parentapp.viewmodel.children;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.service.IconService;
import ca.sfu.fluorine.parentapp.store.ImageInternalStorage;

/**
 * Represents the view model for the children
 */
public class ChildrenListingViewModel extends AndroidViewModel {
    private final ChildDao childDao;
    private final LiveData<List<Child>> childrenLiveData;
    private final LiveData<List<Child>> childrenByCoinFlipsLiveData;
    private final IconService iconService;

    public ChildrenListingViewModel(@NonNull Application application) {
        super(application);
        childDao = AppDatabase.getInstance(application).childDao();
        childrenLiveData = childDao.getAllChildren();
        childrenByCoinFlipsLiveData = childDao.getAllChildrenOrderByRecentCoinFlips();
        iconService = new IconService(ImageInternalStorage.getInstance(application));
    }

    public LiveData<List<Child>> getChildrenLiveData() {
        return childrenLiveData;
    }

    public LiveData<List<Child>> getChildrenByCoinFlipsLiveData() {
        return childrenByCoinFlipsLiveData;
    }

    public Child getNextChild(Child child) {
        return childDao.getNextChildId(child);
    }

    public Bitmap loadBitmapFrom(Child child) {
        return iconService.loadBitmapFrom(child);
    }
}
