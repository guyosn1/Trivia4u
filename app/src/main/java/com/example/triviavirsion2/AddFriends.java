package com.example.triviavirsion2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddFriends extends AppCompatActivity {

    private EditText editTextFriendUsername;
    private Button buttonSendRequest;
    private ListView listViewFriendRequests;
    private ArrayList<String> friendRequestsList;
    private ArrayAdapter<String> adapter;
    private FirebaseAuth auth;
    private DatabaseReference rootRef;
    private String currentUserId;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editTextFriendUsername = findViewById(R.id.editTextFriendUsername);
        buttonSendRequest = findViewById(R.id.buttonSendRequest);
        listViewFriendRequests = findViewById(R.id.listViewFriendRequests);

        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        currentUserId = auth.getCurrentUser().getUid();

        friendRequestsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendRequestsList);
        listViewFriendRequests.setAdapter(adapter);

        loadCurrentUsername();
        loadFriendRequests();

        buttonSendRequest.setOnClickListener(v -> {
            String friendUsername = editTextFriendUsername.getText().toString().trim();
            if (TextUtils.isEmpty(friendUsername)) {
                Toast.makeText(AddFriends.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }
            sendFriendRequest(friendUsername);
        });

        listViewFriendRequests.setOnItemClickListener((parent, view, position, id) -> {
            String requesterUsername = friendRequestsList.get(position);
            acceptFriendRequest(requesterUsername);
        });
    }

    private void loadCurrentUsername() {
        rootRef.child("Users").child(currentUserId).child("username")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentUsername = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFriends.this, "Failed to load username", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadFriendRequests() {
        rootRef.child("friend_requests").child(currentUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friendRequestsList.clear();
                        for (DataSnapshot reqSnapshot : snapshot.getChildren()) {
                            String requesterUsername = reqSnapshot.child("username").getValue(String.class);
                            String status = reqSnapshot.child("status").getValue(String.class);
                            if ("pending".equals(status) && requesterUsername != null) {
                                friendRequestsList.add(requesterUsername);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFriends.this, "Failed to load friend requests.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendFriendRequest(String friendUsername) {
        rootRef.child("Users").orderByChild("username").equalTo(friendUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String friendUserId = userSnapshot.getKey();

                                HashMap<String, Object> requestMap = new HashMap<>();
                                requestMap.put("username", currentUsername);
                                requestMap.put("status", "pending");

                                rootRef.child("friend_requests").child(friendUserId).child(currentUserId)
                                        .setValue(requestMap)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AddFriends.this, "Friend request sent!", Toast.LENGTH_SHORT).show();
                                                editTextFriendUsername.setText("");
                                            } else {
                                                Toast.makeText(AddFriends.this, "Failed to send request", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                break;
                            }
                        } else {
                            Toast.makeText(AddFriends.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFriends.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void acceptFriendRequest(String requesterUsername) {
        rootRef.child("Users").orderByChild("username").equalTo(requesterUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String requesterId = userSnapshot.getKey();

                                // Add both users to each other's friends list
                                rootRef.child("friends").child(currentUserId).child(requesterId).setValue(true);
                                rootRef.child("friends").child(requesterId).child(currentUserId).setValue(true);

                                // Update the friend request status (or remove it)
                                rootRef.child("friend_requests").child(currentUserId).child(requesterId)
                                        .child("status").setValue("accepted");

                                Toast.makeText(AddFriends.this, "Friend request accepted", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddFriends.this, "Requester not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFriends.this, "Error accepting request", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

