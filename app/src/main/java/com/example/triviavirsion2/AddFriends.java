package com.example.triviavirsion2;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriends extends BaseActivity  {

    private EditText editTextFriendMail;
    private Button buttonSendRequest;
    private ListView listViewFriendRequests;
    private ArrayList<String> friendRequestsList;
    private ArrayAdapter<String> adapter;
    private FirebaseAuth auth;
    private DatabaseReference rootRef;
    private String currentUserId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);

        editTextFriendMail = findViewById(R.id.editTextFriendUsername);
        buttonSendRequest = findViewById(R.id.buttonSendRequest);
        listViewFriendRequests = findViewById(R.id.listViewFriendRequests);

        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance("https://trivia-project-8533a-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference();
        currentUserId = auth.getCurrentUser().getUid();

        friendRequestsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendRequestsList);
        listViewFriendRequests.setAdapter(adapter);

        loadCurrentUser();
        loadFriendRequests();

        buttonSendRequest.setOnClickListener(v -> {
            String friendEmail = editTextFriendMail.getText().toString().trim();
            if (TextUtils.isEmpty(friendEmail)) {
                Toast.makeText(AddFriends.this, "Please enter an Email", Toast.LENGTH_SHORT).show();
                return;
            }
            sendFriendRequest(friendEmail);
        });

        listViewFriendRequests.setOnItemClickListener((parent, view, position, id) -> {
            String requesterEmail = friendRequestsList.get(position);
            acceptFriendRequest(requesterEmail);
            friendRequestsList.remove(position);
            adapter.notifyDataSetChanged();

        });
    }

    private void loadCurrentUser() {
        rootRef.child("users").child(currentUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentUser = snapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFriends.this, "Failed to load user", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadFriendRequests() {
        rootRef.child("friend_requests").orderByChild("receiverUid").equalTo(currentUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friendRequestsList.clear();
                        for (DataSnapshot reqSnapshot : snapshot.getChildren()) {
                            var req = reqSnapshot.getValue(FriendRequest.class);
                            String requesterEmail = req.getSenderEmail();
                            friendRequestsList.add(requesterEmail);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFriends.this, "Failed to load friend requests.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendFriendRequest(String friendEmail) {
        var userRef = rootRef.child("users");
        userRef.orderByChild("email").equalTo(friendEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnap = snapshot.getChildren().iterator().next();
                    User u = userSnap.getValue(User.class);

                    // Check if user is in friends list
                    for (DataSnapshot friendSnapshot : userSnap.child("friends").getChildren()) {
                        if (friendSnapshot.getValue(String.class).equals(currentUserId)) {
                            Toast.makeText(AddFriends.this, "User is already in your friends list", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    // Send the request
                    FriendRequest fr = new FriendRequest(currentUserId, u.getUid(), currentUser.getEmail());
                    rootRef.child("friend_requests").child(fr.getKey()).setValue(fr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void acceptFriendRequest(String requesterEmail) {
        // Get the user
        rootRef.child("users").orderByChild("email").equalTo(requesterEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getChildren().iterator().next().getValue(User.class);
                        // Add the user to the current user's friends
                        var uidToAdd = u.getUid();
                        rootRef.child("users").child(currentUserId)
                                .child("friends")
                                .push()
                                .setValue(uidToAdd);
                        rootRef.child("users").child(uidToAdd)
                                .child("friends")
                                .push()
                                .setValue(currentUserId);

                        rootRef.child("friend_requests")
                                .orderByChild("key")
                                .equalTo(uidToAdd + "_" + currentUserId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().removeValue();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}

/**
 * users
 *      uid
 *          ...
 *          friends
 *              ... (random key: uid)
 * friend_requests
 *      key
 *          senderUid: uid
 *          senderEmail: email
 *          receiverUid: uid
 *      ...
 */
