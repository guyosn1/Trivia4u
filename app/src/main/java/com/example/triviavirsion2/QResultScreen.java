package com.example.triviavirsion2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QResultScreen extends AppCompatActivity {

    private static final int[] prizeLevels = {
            500, 1000, 2000, 3000, 5000, 7500, 10000,
            12500, 15000, 25000, 50000, 100000,
            250000, 500000, 1000000
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q_result_screen);

        TextView resultText = findViewById(R.id.resultText);
        LinearLayout prizeListLayout = findViewById(R.id.prizeList);

        boolean isCorrect = getIntent().getBooleanExtra("isCorrect", false);
        int score = getIntent().getIntExtra("score", 0);
        int QuestionIndex=getIntent().getIntExtra("questionIndex",0);

        resultText.setText(isCorrect ? "Correct!" : "Wrong!");
        resultText.setTextColor(isCorrect ? Color.GREEN : Color.RED);

        // ✅ Vibrate only if enabled in settings
        VibrationUtils.vibrate(this, 200);

        int currentStep = Math.min(QuestionIndex, prizeLevels.length - 1);

        // Populate the prize list UI
        prizeListLayout.removeAllViews();
        for (int i = prizeLevels.length - 1; i >= 0; i--) {
            TextView prizeItem = new TextView(this);
            prizeItem.setText("₪ " + String.format("%,d", prizeLevels[i]));
            prizeItem.setTextSize(18);
            prizeItem.setPadding(8, 8, 8, 8);

            if (i == currentStep - 1 && isCorrect) {
                prizeItem.setBackgroundColor(Color.YELLOW);
                prizeItem.setTextColor(Color.BLACK);
            } else {
                prizeItem.setTextColor(Color.DKGRAY);
            }

            prizeListLayout.addView(prizeItem);
        }

        // After delay, go to next screen
        new Handler().postDelayed(() -> {
            if (isCorrect) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("wasCorrect", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Intent gameOver = new Intent(QResultScreen.this, GameOver.class);
                gameOver.putExtra("score", score);
                startActivity(gameOver);
                finish();
            }
        }, 2000);
    }
}


