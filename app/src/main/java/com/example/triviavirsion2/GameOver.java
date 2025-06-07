package com.example.triviavirsion2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

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
}
