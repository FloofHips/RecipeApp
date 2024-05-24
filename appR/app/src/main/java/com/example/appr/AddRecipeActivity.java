package com.example.appr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText nameRec;
    private EditText timeRec;
    private EditText catRec;
    private EditText ingRec;
    private EditText instrRec;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        nameRec = findViewById(R.id.nameRec);
        timeRec = findViewById(R.id.timeRec);
        catRec = findViewById(R.id.catRec);
        ingRec = findViewById(R.id.ingRec);
        instrRec = findViewById(R.id.instrRec);
        userId = getIntent().getIntExtra("userId", -1);
    }

    public void onSaveButtonClick(View view) {
        // Get text from EditText fields

        DBConnect db = new DBConnect(this);

        String name = nameRec.getText().toString();
        String time = timeRec.getText().toString();
        String category = catRec.getText().toString();
        String ingredients = ingRec.getText().toString();
        String instructions = instrRec.getText().toString();

        if (name.isEmpty() || time.isEmpty() || category.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new recipe object
        recipe newRecipe = new recipe();
        newRecipe.setuser(userId);
        newRecipe.setName(name);
        newRecipe.setTime(Integer.parseInt(time));
        newRecipe.setCategories(category);
        newRecipe.setIngredients(ingredients);
        newRecipe.setInstructions(instructions);

        System.out.println("Name: "+newRecipe.getName());
        System.out.println("Time: "+newRecipe.getTime());
        System.out.println("Category: "+newRecipe.getCategories());
        System.out.println("Ingredients: "+newRecipe.getIngredients());
        System.out.println("Instructions: "+newRecipe.getInstru());

        // Add the recipe to the database
        int recid = db.addRecipe(newRecipe);

        recipe dbrecipe = db.getRecipe(recid);

        // Notify the user
        Toast.makeText(this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();

        // Clear the fields
        nameRec.setText("");
        timeRec.setText("");
        catRec.setText("");
        ingRec.setText("");
        instrRec.setText("");

        Intent intent = new Intent(AddRecipeActivity.this, Dashboard.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
