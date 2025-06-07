package com.example.triviavirsion2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Leaderboard extends AppCompatActivity {

    ListView leaderboardListView;

    // Sample data – replace with real scores from a database or API later
    String[] leaderboardData = {
            "🏆 Alice - 1500 pts",
            "🥈 Bob - 1350 pts",
            "🥉 Charlie - 1200 pts",
            "David - 1100 pts",
            "Eva - 1050 pts",
            "Frank - 1000 pts",
            "Grace - 950 pts",
            "Henry - 900 pts"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);  // links to leaderboard.xml

        leaderboardListView = findViewById(R.id.leaderboard_list);

        // Connect data to the ListView using ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                leaderboardData
        );

        leaderboardListView.setAdapter(adapter);
    }
}
