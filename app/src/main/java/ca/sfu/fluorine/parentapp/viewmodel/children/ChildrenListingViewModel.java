package ca.sfu.fluorine.parentapp.viewmodel.children;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.service.IconService;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Represents the view model for the children
 */
@HiltViewModel
public class ChildrenListingViewModel extends ViewModel {
    private final ChildDao childDao;
    private final LiveData<List<Child>> childrenLiveData;
    private final LiveData<List<Child>> childrenByCoinFlipsLiveData;
    private final IconService iconService;

    @Inject
    public ChildrenListingViewModel(@NonNull ChildDao childDao, @NonNull IconService iconService) {
        super();
        this.iconService = iconService;
        this.childDao = childDao;
        childrenLiveData = childDao.getAllChildren();
        childrenByCoinFlipsLiveData = childDao.getAllChildrenOrderByRecentCoinFlips();
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
