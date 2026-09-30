package com.example.smartpantrymanager1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvIngredients, tvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        tvTitle = findViewById(R.id.tvRecipeTitle);
        tvIngredients = findViewById(R.id.tvRecipeIngredients);
        tvSteps = findViewById(R.id.tvRecipeSteps);

        RecipeModel recipe = (RecipeModel) getIntent().getSerializableExtra("RECIPE");

        if (recipe != null) {
            tvTitle.setText(recipe.getName());
            tvIngredients.setText("Required Ingredients: " + recipe.getIngredients());
            tvSteps.setText("Preparation Steps:\n" + recipe.getSteps());
        }
    }
}