package com.example.triviavirsion2;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class Register extends AppCompatActivity {


    private FirebaseAuth auth;
    private DatabaseReference database;
    private EditText signupEmail, signupPassword, retypepassword, username;
    private Button signupButton, buttonAddPicture, buttonUpload;
    private ImageView imageView;
    private Bitmap php;
    ActivityResultLauncher<Intent> activityResultLauncher;         // For camera
    ActivityResultLauncher<Intent> activityResultLauncherUpload;   // For gallery

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://trivia-project-8533a-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("users");

        signupEmail = findViewById(R.id.regemail);
        signupPassword = findViewById(R.id.regpassword);
        retypepassword = findViewById(R.id.regreppassword);
        signupButton = findViewById(R.id.btnregister);
        imageView = findViewById(R.id.image_view);
        buttonAddPicture = findViewById(R.id.button_add_picture);
        buttonUpload = findViewById(R.id.button_upload);
        username = findViewById(R.id.editTextUsername);
        php = null;

        initializeActivityResult();

        // New Intent for movetologin TextView
        TextView moveToLogin = findViewById(R.id.movetologin);
        moveToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });

        signupButton.setOnClickListener(v -> {

            String email = signupEmail.getText().toString();
            String password = signupPassword.getText().toString();
            String name = username.getText().toString();


            if (php == null) {
                php = BitmapFactory.decodeResource(getResources(), R.drawable.usericon);
            }
            if (email.isEmpty()) {
                signupEmail.setError("Email cannot be empty.");
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                signupEmail.setError("Enter a valid email.");
                return;
            }
            if (password.isEmpty()) {
                signupPassword.setError("Password cannot be empty.");
                return;
            }
            if (password.length() < 6) {
                signupPassword.setError("Password must be at least 6 characters.");
                return;
            }
            if (name.isEmpty())
            {
                username.setError("Enter a valid email.");
                return;
            }
            if (!retypepassword.getText().toString().equals(password)) {
                retypepassword.setError("Passwords do not match.");
                return;
            }


            User newUser = new User(email, password, name, php);

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = FirebaseAuth.getInstance().getUid();
                            newUser.setUid(uid);
                            // Add to realtime
                            database.child(uid).setValue(newUser);
                                Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this, LandingPage.class));
                                finish();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                            Toast.makeText(Register.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        buttonAddPicture.setOnClickListener(view -> {
            // Open camera
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(i);
        });
        buttonUpload.setOnClickListener(view -> {
            Intent i = new Intent(MediaStore.ACTION_PICK_IMAGES);
            activityResultLauncherUpload.launch(i);
        });
    }

    private void initializeActivityResult()
    {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap pic = (Bitmap) result.getData().getExtras().get("data");
                        imageView.setImageBitmap(pic);
                        php = pic;
                        imageView.setVisibility(View.VISIBLE);
                    }
                });

        // Gallery
        activityResultLauncherUpload = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            php = BitmapFactory.decodeStream(inputStream);
                            imageView.setImageBitmap(php);
                            imageView.setVisibility(View.VISIBLE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}




