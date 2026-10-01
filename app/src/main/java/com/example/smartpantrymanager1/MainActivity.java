package com.example.smartpantrymanager1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnManagePantry;
    private Button btnViewRecipes;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnManagePantry = findViewById(R.id.btnManagePantry);
        btnViewRecipes = findViewById(R.id.btnViewRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        btnManagePantry.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PantryActivity.class
                    );

            startActivity(intent);
        });

        btnViewRecipes.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            AllRecipesActivity.class
                    );

            startActivity(intent);
        });

        btnSettings.setOnClickListener(view -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });
    }
}