package com.example.appr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class profilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        DBConnect db = new DBConnect(this);

        // Get the userId from the Intent
        int userId = getIntent().getIntExtra("userId", -1);
        String username = db.getName(userId);

// Initialize ArrayLists to hold recipe names, images, and IDs
        ArrayList<String> recipeNames = new ArrayList<>();
        ArrayList<Integer> recipeIds = new ArrayList<>();

// Retrieve all recipes from the database
        List<recipeActivity> allRecipes = db.getAllRec();

// Initialize the next recipe ID
        int nextRecipeId = -1;

// Iterate over the list of recipes
        for (recipeActivity rec : allRecipes) {
            // Check if the recipe belongs to the specified user
            if (rec.getidUser() == userId) {
                // Add recipe details to the ArrayLists
                recipeNames.add(rec.getName());
                recipeIds.add(rec.getid()); // Assuming there's a method to get the recipe ID

                // Update next recipe ID
                if (nextRecipeId == -1 || rec.getid() > nextRecipeId) {
                    nextRecipeId = rec.getid() + 1;
                }
            }
        }




// Add new recipe button click listener
        int finalNextRecipeId = nextRecipeId;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button addNew = findViewById(R.id.saveButton);
        if (addNew != null) {
            addNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profilActivity.this, updateActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("nextRecipeId", finalNextRecipeId);
                    startActivity(intent);
                }
            });
        }




    }

}
