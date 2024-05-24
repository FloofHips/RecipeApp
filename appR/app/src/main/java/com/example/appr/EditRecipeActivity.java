package com.example.appr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditRecipeActivity extends AppCompatActivity {

    private EditText nameRec;
    private EditText timeRec;
    private EditText catRec;
    private EditText ingRec;
    private EditText instrRec;

    private int userId;
    private recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        nameRec = findViewById(R.id.nameRec);
        timeRec = findViewById(R.id.timeRec);
        catRec = findViewById(R.id.catRec);
        ingRec = findViewById(R.id.ingRec);
        instrRec = findViewById(R.id.instrRec);

        // Retrieve the passed recipe object
        currentRecipe = (recipe) getIntent().getSerializableExtra("recipe");
        userId = getIntent().getIntExtra("userId", -1);

        // Populate the fields with current recipe details
        if (currentRecipe != null) {
            nameRec.setText(currentRecipe.getName());
            timeRec.setText(String.valueOf(currentRecipe.getTime()));
            catRec.setText(currentRecipe.getCategories());
            ingRec.setText(currentRecipe.getIngredients());
            instrRec.setText(currentRecipe.getInstru());
        }
    }

    public void onEditButtonClick(View view) {
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

        // Update the current recipe object
        currentRecipe.setName(name);
        currentRecipe.setTime(Integer.parseInt(time));
        currentRecipe.setCategories(category);
        currentRecipe.setIngredients(ingredients);
        currentRecipe.setInstructions(instructions);

        // Update the recipe in the database
        int recid = db.updateRecipe(currentRecipe);

        // Notify the user
        if (recid > 0) {
            Toast.makeText(this, "Recipe changed successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Recipe update failed", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(EditRecipeActivity.this, Dashboard.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
