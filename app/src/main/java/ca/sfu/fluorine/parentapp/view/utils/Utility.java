package ca.sfu.fluorine.parentapp.view.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;

/**
 * Provides utility for the general application functions
 *
 * All functions should be static
 */
public class Utility {
    public static AlertDialog.Builder createConfirmDialogBuilder(Context context) {
        return new AlertDialog.Builder(context)
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) ->
                    dialogInterface.dismiss()
                );
    }

    public static void makeConfirmDialog(
            Context context,
            @StringRes int titleId,
            @StringRes int messageId,
            @NonNull DialogInterface.OnClickListener confirmAction
    ) {
        createConfirmDialogBuilder(context)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(android.R.string.ok, confirmAction)
                .show();
    }

    public static void setupImage(Context context, ImageView imageView, @NonNull Child child) {
        if (child.getId() == Child.getUnspecifiedChild().getId()) {
            imageView.setVisibility(ImageView.INVISIBLE);
        } else {
            imageView.setVisibility(ImageView.VISIBLE);
            Bitmap image = ImageInternalStorage.getInstance(context)
                    .loadImage(child.getPhotoFileName());
            if (image == null) {
                imageView.setImageResource(R.drawable.robot);
            } else {
                imageView.setImageBitmap(image);
            }
        }
    }

    public static String formatChildName(Context context, @NonNull Child child) {
        if (child.getId() == Child.getUnspecifiedChild().getId()) {
            return context.getString(R.string.no_children);
        } else {
            return context.getString(R.string.full_name, child.getFirstName(), child.getLastName());
        }
    }
}
