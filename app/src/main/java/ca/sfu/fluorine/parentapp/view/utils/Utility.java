package ca.sfu.fluorine.parentapp.view.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

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
}
