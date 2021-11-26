package ca.sfu.fluorine.parentapp.service;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.UUID;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.store.ImageInternalStorage;
import ca.sfu.fluorine.parentapp.R;

public class IconService {
    ImageInternalStorage storage;

    @Inject
    public IconService(ImageInternalStorage storage) {
        this.storage = storage;
    }

    public void saveImageToChild(Child child, @Nullable Bitmap bitmap) {
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

    public void deleteImageFromChild(Child child) {
        storage.deleteImage(child.getPhotoFileName());
    }

    public Bitmap updateChildImageView(@NonNull Child child, @NonNull ImageView childIconView) {
        if (child.getId() == Child.getUnspecifiedChild().getId()) {
            childIconView.setVisibility(ImageView.INVISIBLE);
            return null;
        }
        childIconView.setVisibility(ImageView.VISIBLE);
        Bitmap bitmap = storage.loadImage(child.getPhotoFileName());
        if (bitmap == null) {
            childIconView.setImageResource(R.drawable.default_icon);
        } else {
            childIconView.setImageBitmap(bitmap);
        }
        return bitmap;
    }
}
