package com.example.triviavirsion2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationUtils {

    public static void vibrate(Context context, long milliseconds) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean isVibrationEnabled = prefs.getBoolean("vibration_enabled", true);

        if (!isVibrationEnabled) return;

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }
}
