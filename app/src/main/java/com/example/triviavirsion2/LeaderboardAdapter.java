package com.example.triviavirsion2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private List<User> users;

    public LeaderboardAdapter(List<User> users) {
        this.users = users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, score;

        public ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.usernameTextView);
            score = view.findViewById(R.id.scoreTextView);
        }
    }

    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText((position + 1) + ". " + user.getUsername());
        holder.score.setText(String.valueOf(user.getScore()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}


