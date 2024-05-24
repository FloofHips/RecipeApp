package com.example.appr;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView recipeName, recipeCategory, recipeTime, recipeIngredients, recipeInstructions;
    private recipe selectedRecipe;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeName = findViewById(R.id.recipeName);
        recipeCategory = findViewById(R.id.recipeCategory);
        recipeTime = findViewById(R.id.recipeTime);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeInstructions = findViewById(R.id.recipeInstructions);
        FloatingActionButton editButton = findViewById(R.id.editButton);
        FloatingActionButton deleteButton = findViewById(R.id.deleteButton);
        FloatingActionButton shareButton = findViewById(R.id.shareButton);
        FloatingActionButton faveButton = findViewById(R.id.faveButton);
        ImageView isFavoriteImage = findViewById(R.id.isFavorite);

        selectedRecipe = (recipe) getIntent().getSerializableExtra("recipe");
        currentUserId = getIntent().getIntExtra("currentUserId", -1);

        if (currentUserId==-1){
            faveButton.setVisibility(View.GONE);
        }

        if (selectedRecipe.getidUser() == currentUserId) {
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }
        DBConnect db = new DBConnect(RecipeDetailsActivity.this);
        AtomicBoolean isFavorite = new AtomicBoolean(db.isFavorite(currentUserId, selectedRecipe));

        isFavoriteImage.setVisibility(isFavorite.get() ? View.VISIBLE : View.GONE);

        editButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(RecipeDetailsActivity.this, EditRecipeActivity.class);
            editIntent.putExtra("recipe", selectedRecipe);
            startActivity(editIntent);
        });

        faveButton.setOnClickListener(v -> {
            //DBConnect db = new DBConnect(RecipeDetailsActivity.this);
            isFavorite.set(db.isFavorite(currentUserId, selectedRecipe));

            if (isFavorite.get()) {
                db.deleteFavorite(currentUserId, selectedRecipe);
                isFavoriteImage.setVisibility(View.GONE);
            } else {
                db.addFavorite(currentUserId, selectedRecipe);
                isFavoriteImage.setVisibility(View.VISIBLE);
            }
        });

        deleteButton.setOnClickListener(v -> {
            //DBConnect db = new DBConnect(RecipeDetailsActivity.this);
            db.deleteRecipe(selectedRecipe);
            Toast.makeText(RecipeDetailsActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, generateShareText(selectedRecipe));
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Share Recipe"));
        });
        recipe rec = (recipe) getIntent().getSerializableExtra("recipe");
        if (rec != null) {
            recipeName.setText(rec.getName());
            recipeCategory.setText(rec.getIngredients());
            recipeTime.setText("Preparation Time: " + rec.getTime() + " minutes");
            recipeIngredients.setText("Ingredients: " + rec.getCategories());
            recipeInstructions.setText("Instructions: " + rec.getInstru());
        }
    }

    private String generateShareText(recipe rec) {
        return "Check out this recipe!\n\n" +
                "It's a " + rec.getCategories() +
                " dish called: " + rec.getName() +
                ". You could make it in only : " + rec.getTime() + " minutes! \n" +
                "Ingredients: " + rec.getIngredients() + "\n" +
                "Instructions: " + rec.getInstru();
    }
}
