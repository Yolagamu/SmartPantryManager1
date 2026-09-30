package com.example.smartpantrymanager1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    ListView listViewRecipes;
    ArrayList<String> recipeList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        listViewRecipes = findViewById(R.id.listViewRecipes);

        // Populate sample recipes (you can later fetch these from database helper if needed)
        recipeList = new ArrayList<>();
        recipeList.add("Pasta Bolognese - Uses: Pasta, Tomato, Ground Beef");
        recipeList.add("Vegetable Stir Fry - Uses: Mixed Veggies, Soy Sauce");
        recipeList.add("Cheese Omelet - Uses: Eggs, Cheese, Butter");

        // Set adapter to display recipes
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeList);
        listViewRecipes.setAdapter(adapter);
    }
}
