package com.example.appr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        DBConnect db = new DBConnect(this);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button profil = findViewById(R.id.profil);


        // Get the userId from the Intent
        int userId = getIntent().getIntExtra("userId", -1);
        String username = db.getName(userId);
        TextView welcomeTextView = findViewById(R.id.Title);
        welcomeTextView.setText("Recetta ");


        // Initialize ArrayLists to hold recipe names and images
        ArrayList<String> recipeNames = new ArrayList<>();
        ArrayList<String> recipeImages = new ArrayList<>();
        ArrayList<Integer> recipeIds = new ArrayList<>();

        // Retrieve all recipes from the database
        // Retrieve all recipes from the database
        List<recipeActivity> allRecipes = db.getAllRec();

// Iterate over the list of recipes to populate the ArrayLists
        for (recipeActivity rec : allRecipes) {
            recipeNames.add(rec.getName());
            recipeIds.add(rec.getid()); // Assuming there's a method to get the recipe ID

            // Debugging toast messages
            Toast.makeText(this, "Added recipe: " + rec.getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Added recipe ID: " + rec.getid(), Toast.LENGTH_SHORT).show();
        }

// Create the adapter with the ArrayLists

// Set the adapter to the ListView



/*
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Dashboard.this, profil.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

            }
        });*/


    }
    // Méthode pour le clic sur le texte "Invité"
    public void onGuestAccessClicked(View view) {
        // Rediriger vers l'activité pour ajouter une recette
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
    }





}
