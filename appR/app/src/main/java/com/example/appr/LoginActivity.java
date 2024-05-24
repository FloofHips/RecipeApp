package com.example.appr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DBConnect db = new DBConnect(this);

        // Find the CardViews
        Button loginButton = findViewById(R.id.register);
        Button registerButton = findViewById(R.id.alrAccount);


        // Find the guest access TextView
        //@SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView guestAccessTextView = findViewById(R.id.guestAccess);

        // Set OnClickListener to the Login CardView
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve username and password from TextViews
                TextView usernameTextView = findViewById(R.id.username);
                TextView passwordTextView = findViewById(R.id.password);

                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                // Check if username or password fields are empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                } else {
                    // Call validCred function to check credentials
                    int userId = db.validCred(username, password);

                    if (userId > -1) {
                        // If userId is valid, redirect to DashboardActivity with userId
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    } else {
                        // If credentials are invalid, show a message
                        Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou mot de passe invalide !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
