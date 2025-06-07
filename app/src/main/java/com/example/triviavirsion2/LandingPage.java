package com.example.triviavirsion2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity implements View.OnClickListener {

    Button btnsettings, btnleaderboard, btnaddfriends, btnplaygame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // כפתור הגדרות
        btnsettings = findViewById(R.id.btnsettings);
        btnsettings.setOnClickListener(this);

        // כפתור Leaderboard
        btnleaderboard = findViewById(R.id.btnleaderboard);
        btnleaderboard.setOnClickListener(this);

        // כפתור הוספת חברים
        btnaddfriends = findViewById(R.id.btnaddfriends);
        btnaddfriends.setOnClickListener(this);

        btnplaygame = findViewById(R.id.btnplaygame);
        btnplaygame.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btnsettings) {
            Intent intent = new Intent(LandingPage.this, SettingsPage.class);
            startActivity(intent);
        } else if (view == btnleaderboard) {
            Intent intent = new Intent(LandingPage.this, Leaderboard.class);
            startActivity(intent);
        } else if (view == btnaddfriends) {
            Intent intent = new Intent(LandingPage.this, AddFriends.class);
            startActivity(intent);
        }
        else if (view == btnplaygame) {
            startActivity(new Intent(LandingPage.this, PlayGame.class));
        }
    }
}






