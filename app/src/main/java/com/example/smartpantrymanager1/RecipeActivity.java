package com.example.smartpantrymanager1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listViewRecipes;
    private TextView tvEmptyMessage;

    private ArrayList<RecipeModel> matchedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);

        databaseHelper = new DatabaseHelper(this);

        listViewRecipes = findViewById(R.id.listViewRecipes);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        loadMatchedRecipes();
    }

    private void loadMatchedRecipes() {

        matchedRecipes =
                databaseHelper.getStrictMatchedRecipes();

        if (matchedRecipes == null ||
                matchedRecipes.isEmpty()) {

            tvEmptyMessage.setText(
                    "No recipes match your pantry yet - add more ingredients!"
            );

            tvEmptyMessage.setVisibility(View.VISIBLE);

            listViewRecipes.setVisibility(View.GONE);

        } else {

            tvEmptyMessage.setVisibility(View.GONE);

            listViewRecipes.setVisibility(View.VISIBLE);

            ArrayList<String> recipeNames =
                    new ArrayList<>();

            for (RecipeModel recipe : matchedRecipes) {

                recipeNames.add(recipe.getName());
            }

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_list_item_1,
                            recipeNames
                    );

            listViewRecipes.setAdapter(adapter);

            listViewRecipes.setOnItemClickListener(
                    (parent, view, position, id) -> {

                        RecipeModel selectedRecipe =
                                matchedRecipes.get(position);

                        Intent intent = new Intent(
                                RecipeActivity.this,
                                RecipeDetailActivity.class
                        );

                        intent.putExtra(
                                "RECIPE",
                                selectedRecipe
                        );

                        startActivity(intent);
                    }
            );
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (databaseHelper != null) {
            loadMatchedRecipes();
        }
    }
}