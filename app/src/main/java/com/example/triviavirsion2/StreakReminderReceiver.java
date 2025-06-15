package com.example.triviavirsion2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class StreakReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // User not logged in -> Do nothing
            return;
        }
        DatabaseReference database = FirebaseDatabase.getInstance("https://trivia-project-8533a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        DatabaseReference streakRef = database.child("users").child(user.getUid()).child("streak");
        streakRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Streak streak = task.getResult().getValue(Streak.class);
                if (streak != null) {
                    long lastUpdatedTimestamp = streak.getTimestamp();
                    LocalDate lastDate = Instant.ofEpochMilli(lastUpdatedTimestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    LocalDate today = LocalDate.now();
                    if (!lastDate.equals(today)) {
                        // 2. Not updated today -> Send notification
                        sendReminderNotification(context);
                    }

                }
            }
        });
    }


    private void sendReminderNotification(Context context) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "streak_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Don't lose your streak!")
                .setContentText("You haven’t updated your streak today. Tap now to keep it going!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1001, builder.build());
    }

}
