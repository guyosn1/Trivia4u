package com.example.triviavirsion2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is already logged in, navigate to LandingPage
            Intent intent = new Intent(MainActivity.this, LandingPage.class);
            startActivity(intent);
            finish();
        }


        loginButton = findViewById(R.id.btnlogin);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });
        registerButton = findViewById(R.id.btnregister);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);


        });

    }
}
