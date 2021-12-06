package ca.sfu.fluorine.parentapp.viewmodel.children;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.service.IconService;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Represents a view model for the add or edit children screen
 */
@HiltViewModel
public class ChildrenEditViewModel extends ViewModel {
    private final ChildDao childDao;
    private final IconService iconService;

    @Inject
    public ChildrenEditViewModel(ChildDao childDao, IconService iconService) {
        this.childDao = childDao;
        this.iconService = iconService;
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

    public Bitmap loadBitmapFrom(Child child) {
        return iconService.loadBitmapFrom(child);
    }
}
