package ca.sfu.fluorine.parentapp.service;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.UUID;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.store.ImageInternalStorage;

public class IconService {
    private final ImageInternalStorage storage;

    @Inject
    public IconService(ImageInternalStorage storage) {
        this.storage = storage;
    }

    public void saveImageToChild(@NonNull Child child, @Nullable Bitmap bitmap) {
        String fileName = child.getPhotoFileName();
        if (bitmap == null) {
            storage.deleteImage(fileName);
            child.setPhotoFileName(null);
        } else {
            if (fileName == null) {
                fileName = UUID.randomUUID().toString();
                storage.saveImage(fileName, bitmap);
            }
            child.setPhotoFileName(fileName);
        }
    }

    public void deleteImageFromChild(@NonNull Child child) {
        storage.deleteImage(child.getPhotoFileName());
    }

    public Bitmap loadBitmapFrom(@Nullable Child child) {
        if (child == null || child.getId() == Child.getUnspecifiedChild().getId()) {
            return null;
        }
        return storage.loadImage(child.getPhotoFileName());
    }
}
