package com.example.triviavirsion2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        // ⬅️ עדכון סטריק
        updateStreak();

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

    private void updateStreak() {
        SharedPreferences prefs = getSharedPreferences("triviaPrefs", MODE_PRIVATE);
        long lastPlayed = prefs.getLong("lastPlayed", 0);
        int streak = prefs.getInt("streak", 0);

        Calendar today = Calendar.getInstance();
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTimeInMillis(lastPlayed);

        // מנקים שעה כדי להשוות רק תאריך
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);

        long diff = (today.getTimeInMillis() - lastDate.getTimeInMillis()) / (1000 * 60 * 60 * 24);

        if (diff == 1) {
            streak += 1; // המשיך רצף
        } else if (diff > 1) {
            streak = 1; // התחיל מחדש
        } // אם diff == 0 – כבר שיחק היום, לא עושים כלום

        // שמירה
        prefs.edit()
                .putLong("lastPlayed", System.currentTimeMillis())
                .putInt("streak", streak)
                .apply();
    }
}

