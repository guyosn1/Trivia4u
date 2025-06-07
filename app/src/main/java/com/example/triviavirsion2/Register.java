package com.example.triviavirsion2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class Register extends AppCompatActivity {


    private FirebaseAuth auth;
    private DatabaseReference database;


    private EditText signupEmail, signupPassword, retypepassword;
    private Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");

        signupEmail = findViewById(R.id.regemail);
        signupPassword = findViewById(R.id.regpassword);
        retypepassword = findViewById(R.id.regreppassword);
        signupButton = findViewById(R.id.btnregister);

        // New Intent for movetologin TextView
        TextView moveToLogin = findViewById(R.id.movetologin);
        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your existing signup code...
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();

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
                if (!retypepassword.getText().toString().equals(password)) {
                    retypepassword.setError("Passwords do not match.");
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                    Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, LandingPage.class));
                                    finish();
                                } else {
                                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                                    Toast.makeText(Register.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}




