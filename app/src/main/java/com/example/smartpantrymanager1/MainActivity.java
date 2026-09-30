
package com.example.smartpantrymanager1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnPantry, btnRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI elements to Java variables
        btnPantry = findViewById(R.id.btnPantry);
        btnRecipes = findViewById(R.id.btnRecipes);

        // Navigate to Pantry Management screen when clicked
        btnPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PantryActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to Recipes screen when clicked
        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                startActivity(intent);
            }
        });
    }
}