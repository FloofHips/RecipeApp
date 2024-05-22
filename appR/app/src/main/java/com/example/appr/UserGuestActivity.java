package com.example.appr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserGuestActivity extends AppCompatActivity {

    private ListView recipeListView;
    private DBConnect dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guest);

        dbHelper = new DBConnect(this);
        recipeListView = findViewById(R.id.recipeListView);

        loadRecipes();
    }

    private void loadRecipes() {
        List<String> recipes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "nameRec",
                "timeRec",
                "catRec",
                "ingRec",
                "instrRec"
        };

        Cursor cursor = db.query(
                "recipe",   // The table to query
                projection, // The array of columns to return (pass null to get all)
                null,       // The columns for the WHERE clause
                null,       // The values for the WHERE clause
                null,       // Don't group the rows
                null,       // Don't filter by row groups
                null        // The sort order
        );

        try {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("nameRec"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("timeRec"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("catRec"));
                String ingredients = cursor.getString(cursor.getColumnIndexOrThrow("ingRec"));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow("instrRec"));

                String recipeDetails = "Name: " + name + "\n" +
                        "Time: " + time + " mins\n" +
                        "Category: " + category + "\n" +
                        "Ingredients: " + ingredients + "\n" +
                        "Instructions: " + instructions;

                recipes.add(recipeDetails);
            }
        } finally {
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipes);
        recipeListView.setAdapter(adapter);
    }
}
