package com.example.triviavirsion2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

public class GameOver extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance("https://trivia-project-8533a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        firebaseAuth = FirebaseAuth.getInstance();

        // עדכון סטריק
        getCurrentStreakAndUpdate();

        int score = getIntent().getIntExtra("score", 0);
        TextView finalScore = findViewById(R.id.finalScore);
        finalScore.setText("Your Score: " + score);

        Button backToHome = findViewById(R.id.backToHome);
        backToHome.setOnClickListener(v -> {
            Intent i = new Intent(GameOver.this, LandingPage.class);
            i.putExtra("score", score);
            startActivity(i);
            finish();
        });
    }

    private void updateStreak(Streak streak) {
        var ref = database.child("users").child(firebaseAuth.getUid()).child("streak");
        ref.setValue(streak);
    }
    public void getCurrentStreakAndUpdate() {
        var streakRef = database.child("users").child(firebaseAuth.getUid()).child("streak");
        streakRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                var streak = task.getResult().getValue(Streak.class);
                if (streak != null) {
                    long lastTimeStamp = streak.getTimestamp();
                    LocalDate today = LocalDate.now();
                    LocalDate lastDate = Instant.ofEpochMilli(lastTimeStamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    if (lastDate.plusDays(1).equals(today)) {
                        updateStreak(new Streak(streak.getDays() + 1));
                        scheduleStreakCheckAlarm(this);
                    }
                    else if (lastDate.isBefore(today))
                    {
                        updateStreak(new Streak(1));
                    }
                }
                else {
                    Streak newStreak = new Streak(1);
                    updateStreak(newStreak);
                    scheduleStreakCheckAlarm(this);
                }
            }
        });
    }

    public void scheduleStreakCheckAlarm(Context context) {
        // Trigger time: tomorrow at 20:00
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(context, StreakReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+
            if (!alarmManager.canScheduleExactAlarms()) {
                // You need to ask the user to allow it manually
                Intent settings = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                context.startActivity(settings);
                return;
            }
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
    }

}

