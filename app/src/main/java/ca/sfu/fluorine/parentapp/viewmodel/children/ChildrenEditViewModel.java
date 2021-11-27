package ca.sfu.fluorine.parentapp.viewmodel.children;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.children.ChildDao;
import ca.sfu.fluorine.parentapp.service.IconService;
import ca.sfu.fluorine.parentapp.store.ImageInternalStorage;

public class ChildrenEditViewModel extends AndroidViewModel {
    private final ChildDao childDao;
    private final IconService iconService;

    public ChildrenEditViewModel(@NonNull Application application) {
        super(application);
        childDao = AppDatabase.getInstance(application).childDao();
        iconService = new IconService(ImageInternalStorage.getInstance(application));
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
