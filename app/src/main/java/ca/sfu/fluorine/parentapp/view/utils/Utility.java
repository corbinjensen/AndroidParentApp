package ca.sfu.fluorine.parentapp.view.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.Date;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;

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

    public static void setupImage(
            @NonNull Child child,
            @Nullable Bitmap bitmap,
            ImageView imageView) {
        if (child.getId() == Child.getUnspecifiedChild().getId()) {
            imageView.setVisibility(ImageView.INVISIBLE);
        } else {
            imageView.setVisibility(ImageView.VISIBLE);
            if (bitmap == null) {
                imageView.setImageResource(R.drawable.default_icon);
            } else {
                imageView.setImageBitmap(bitmap);
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

    public static TextWatcher makeTextWatcher(Runnable callbackOnChange) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                callbackOnChange.run();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public static String formatUnixTime(long unixTime) {
        return DateFormat
            .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
            .format(new Date(unixTime));
    }
}
