package com.example.appr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    private ListView recipeListView;
    private List<recipe> originalRecipeList;
    private DBConnect db;
    private SearchView searchView;
    private RecipeAdapter adapter;
    private DBConnect dbConnect;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        db = new DBConnect(this);
        recipeListView = findViewById(R.id.recipeListView);
        userId = getIntent().getIntExtra("userId", -1);
        searchView = findViewById(R.id.searchView);
        loadRecipes();

        // Get the userId from the Intent
        String username = db.getName(userId);
        FloatingActionButton addButton = findViewById(R.id.addButton);
        FloatingActionButton reloadButton = findViewById(R.id.reloadButton);

        if (userId == -1) {
            addButton.setVisibility(View.GONE);
        } else {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard.this, AddRecipeActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            });
        }

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRecipes();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return true;
            }
        });
    }

    private void filterRecipes(String newText) {
        List<recipe> filteredList = new ArrayList<>();
        for (recipe rec : originalRecipeList) {
            if (rec.getName().toLowerCase().contains(newText.toLowerCase()) ||
                    rec.getCategories().toLowerCase().contains(newText.toLowerCase()) ||
                    rec.getIngredients().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(rec);
            }
        }
        if(Objects.equals(newText, ""))
            loadRecipes();
        else
            adapter.updateList(filteredList);
    }

    private void loadRecipes() {
        originalRecipeList = db.getAllRec();
        adapter = new RecipeAdapter(this, originalRecipeList, userId);
        recipeListView.setAdapter(adapter);
    }
}
