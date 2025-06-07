package com.example.triviavirsion2;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogHelper {

    public static void showErrorDialog(Context context, String title, String message) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_error)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

}
