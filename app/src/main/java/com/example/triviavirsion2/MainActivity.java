package com.example.triviavirsion2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
