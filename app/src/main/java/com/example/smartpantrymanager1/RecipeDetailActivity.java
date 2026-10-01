package com.example.smartpantrymanager1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView tvRecipeName;
    private TextView tvIngredients;
    private TextView tvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvSteps = findViewById(R.id.tvSteps);

        RecipeModel recipe =
                (RecipeModel) getIntent().getSerializableExtra("RECIPE");

        if (recipe != null) {

            tvRecipeName.setText(recipe.getName());

            tvIngredients.setText(
                    "Ingredients:\n" + recipe.getIngredients()
            );

            tvSteps.setText(
                    "Preparation Steps:\n" + recipe.getInstructions()
            );
        }
    }
}