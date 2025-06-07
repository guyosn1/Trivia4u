package com.example.triviavirsion2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        landing_score = findViewById(R.id.landing_score);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://trivia-project-8533a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        int addedScore = getIntent().getIntExtra("score", -1);
        String uid = firebaseAuth.getUid();
        var ref = database.child("users").child(uid).child("score");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int old = snapshot.getValue(Integer.class);
                    int newScore = old;
                    if (addedScore != -1) {
                        newScore += addedScore;
                        ref.setValue(newScore);
                    }
                    landing_score.setText("Your score: " + newScore);
                }
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






