
package com.example.smartpantrymanager1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllRecipesActivity extends AppCompatActivity {

    private ListView listViewAllRecipes;

    private DatabaseHelper databaseHelper;

    private ArrayList<RecipeModel> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_recipes);

        listViewAllRecipes = findViewById(R.id.listViewAllRecipes);

        databaseHelper = new DatabaseHelper(this);

        loadRecipes();
    }

    private void loadRecipes() {

        recipes = databaseHelper.getAllRecipes();

        ArrayList<String> recipeNames = new ArrayList<>();

        for (RecipeModel recipe : recipes) {
            recipeNames.add(recipe.getName());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        recipeNames
                );

        listViewAllRecipes.setAdapter(adapter);

        listViewAllRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {

                    RecipeModel selectedRecipe =
                            recipes.get(position);

                    Intent intent =
                            new Intent(
                                    AllRecipesActivity.this,
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