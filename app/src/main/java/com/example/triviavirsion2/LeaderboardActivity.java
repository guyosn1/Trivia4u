package com.example.triviavirsion2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView leaderboardRecyclerView;
    private LeaderboardAdapter adapter;
    DatabaseReference database;

    private static final int MAX_LEADERBOARD = 15;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        database = FirebaseDatabase.getInstance("https://trivia-project-8533a-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView);
        leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchLeaderboard();
    }

    private void fetchLeaderboard() {
        DatabaseReference dbRef = database.child("users");

        dbRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                Log.d("Leaderboard", "DataSnapshot children count: " + snapshot.getChildrenCount());

                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    User user = userSnap.getValue(User.class);
                    if (user != null) {
                        Log.d("Leaderboard", "User loaded: " + user.getUsername() + " - " + user.getScore());
                        userList.add(user);
                    } else {
                        Log.d("Leaderboard", "Null user found in snapshot");
                    }
                }

                // Sort by score descending
                Collections.sort(userList, (u1, u2) ->
                        Integer.compare(u2.getScore(), u1.getScore()));

                // Limit to top 10
                if (userList.size() > MAX_LEADERBOARD) {
                    userList = userList.subList(0, MAX_LEADERBOARD);
                }

                adapter = new LeaderboardAdapter(userList);
                leaderboardRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Leaderboard", "Firebase error", error.toException());
            }
        });
    }
}
