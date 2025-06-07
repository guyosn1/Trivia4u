package com.example.triviavirsion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword, loginrepPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        auth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.loginemail);
        loginPassword = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.btnlogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (isInputValid(email, password)) {
            signInWithEmailPassword(email, password);
        }
    }

    private boolean isInputValid(String email, String password) {
        if (email.isEmpty()) {
            loginEmail.setError("Email cannot be empty.");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Enter a valid email.");
            return false;
        }
        if (password.isEmpty()) {
            loginPassword.setError("Password cannot be empty.");
            return false;
        }
        return true;
    }

    private void signInWithEmailPassword(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();

                            Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login.this, LandingPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                            Toast.makeText(Login.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }}


