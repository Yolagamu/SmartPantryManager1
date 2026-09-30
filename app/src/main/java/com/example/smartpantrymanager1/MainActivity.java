package com.example.smartpantrymanager1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnManagePantry, btnViewRecipes, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnManagePantry = findViewById(R.id.btnManagePantry);
        btnViewRecipes = findViewById(R.id.btnViewRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        btnManagePantry.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PantryActivity.class));
        });

        btnViewRecipes.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RecipeActivity.class));
        });

        btnSettings.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });
    }
}