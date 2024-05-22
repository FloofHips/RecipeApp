package com.example.appr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DBConnect db = new DBConnect(this);

        // Find the CardView
        CardView loginCardView = findViewById(R.id.loginCardView);

        TextView b1;
        EditText d1, d2, d3;

        b1 = findViewById(R.id.bRegister);
        d1 = findViewById(R.id.getNomR);
        d2 = findViewById(R.id.getMotDePasseR);
        d3 = findViewById(R.id.getMotDePasseconfirmR);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = d1.getText().toString().trim();
                String password = d2.getText().toString().trim();
                String confirmPassword = d3.getText().toString().trim();

                if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Veuillez remplir tout les champs", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Les mots de passe ne sont pas identiques", Toast.LENGTH_SHORT).show();
                } else {
                    db.addContact(new UserActivity(name, password));
                    d1.setText(null);
                    d2.setText(null);
                    d3.setText(null);
                    Toast.makeText(RegisterActivity.this, "Compte crée avec succès, veuillez se connecter", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set OnClickListener to the CardView
        loginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
