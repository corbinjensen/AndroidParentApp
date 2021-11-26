package ca.sfu.fluorine.parentapp.viewmodel;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public class ChildrenViewModel extends ViewModel {
    private final ChildDao childDao;
    private final LiveData<List<Child>> childrenLiveData;
    private final LiveData<List<Child>> childrenByCoinFlipsLiveData;
    private final IconService iconService;

    @Inject
    public ChildrenViewModel(@NonNull ChildDao childDao, @NonNull IconService iconService) {
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

    @Nullable
    public Child getChildById(int id) {
        return childDao.getChildById(id);
    }

    public void updateChild(Child child, @Nullable Bitmap icon) {
        iconService.saveImageToChild(child, icon);
        childDao.update(child);
    }

    public void addChild(Child child, @Nullable Bitmap icon) {
        iconService.saveImageToChild(child, icon);
        childDao.add(child);
    }

    public void deleteChild(Child child) {
        iconService.deleteImageFromChild(child);
        childDao.delete(child);
    }

    public Child getNextChild(Child child) {
        return childDao.getNextChildId(child);
    }

    public IconService getIconService() {
        return iconService;
    }
}
