package com.example.triviavirsion2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandingPage extends AppCompatActivity implements View.OnClickListener {

    Button btnsettings, btnleaderboard, btnaddfriends, btnplaygame;
    TextView landing_score;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // כפתורי ניווט
        btnsettings = findViewById(R.id.btnsettings);
        btnleaderboard = findViewById(R.id.btnleaderboard);
        btnaddfriends = findViewById(R.id.btnaddfriends);
        btnplaygame = findViewById(R.id.btnplaygame);

        // מאזינים לכפתורים
        btnsettings.setOnClickListener(this);
        btnleaderboard.setOnClickListener(this);
        btnaddfriends.setOnClickListener(this);
        btnplaygame.setOnClickListener(this);

        landing_score = findViewById(R.id.landing_score);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://trivia-project-8533a-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference();

        String uid = firebaseAuth.getUid();

        // בדיקה שהמשתמש קיים לפני שממשיכים
        if (uid == null) {
            DialogHelper.showErrorDialog(LandingPage.this, "Error", "User not logged in.");
            return;
        }

        // קבלת ניקוד אם הגיע ממסך אחר
        int addedScore = getIntent().getIntExtra("score", -1);

        DatabaseReference ref = database.child("users").child(uid).child("score");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int newScore = 0;
                if (snapshot.exists()) {
                    Integer old = snapshot.getValue(Integer.class);
                    if (old != null) newScore = old;
                }
                if (addedScore != -1) {
                    newScore += addedScore;
                    ref.setValue(newScore);
                }
                landing_score.setText("Your score: " + newScore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                DialogHelper.showErrorDialog(LandingPage.this, "Error", "Failed to update score.");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnsettings) {
            startActivity(new Intent(LandingPage.this, SettingsPage.class));
        } else if (view == btnleaderboard) {
            startActivity(new Intent(LandingPage.this, LeaderboardActivity.class));
        } else if (view == btnaddfriends) {
            startActivity(new Intent(LandingPage.this, AddFriends.class));
        } else if (view == btnplaygame) {
            startActivity(new Intent(LandingPage.this, PlayGame.class));
        }
    }
}




