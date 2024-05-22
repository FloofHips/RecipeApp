package com.example.appr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class updateActivity extends AppCompatActivity {

    private Button confirmB;
    private EditText nomD;
    private EditText timeD;
    private EditText catD;
    private EditText ingD;
    private EditText insD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        // Initialize views
        confirmB = findViewById(R.id.saveButton);
        nomD = findViewById(R.id.nameRec);
        timeD = findViewById(R.id.timeRec);
        catD = findViewById(R.id.catRec);
        ingD = findViewById(R.id.ingRec);
        insD = findViewById(R.id.instrRec);

        // Set click listener for the confirm button
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input values from EditText fields
                String name = nomD.getText().toString().trim();
                int time = Integer.parseInt(timeD.getText().toString().trim());
                String category = catD.getText().toString().trim();
                String ingredients = ingD.getText().toString().trim();
                String instructions = insD.getText().toString().trim();

                // Check if any field is empty
                if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                    Toast.makeText(updateActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform update operation or any other necessary action here
                // You can use the retrieved values to update the recipe
                // For example:
                // 1. Get the recipe ID from the Intent
                // 2. Update the recipe in the database with the new values

                // Display a toast message indicating that the changes are saved
                Toast.makeText(updateActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();

                // Finish the activity
                finish();
            }
        });
    }
}
