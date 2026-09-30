package com.example.smartpantrymanager1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView listViewRecipes;
    TextView tvEmptyMessage;
    List<RecipeModel> matchedRecipes;

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
        matchedRecipes = databaseHelper.getStrictMatchedRecipes();

        if (matchedRecipes.isEmpty()) {
            tvEmptyMessage.setText("No recipes match your pantry yet - add more ingredients!");
            tvEmptyMessage.setVisibility(TextView.VISIBLE);
            listViewRecipes.setVisibility(ListView.GONE);
        } else {
            tvEmptyMessage.setVisibility(TextView.GONE);
            listViewRecipes.setVisibility(ListView.VISIBLE);

            List<String> recipeNames = new ArrayList<>();
            for (RecipeModel r : matchedRecipes) {
                recipeNames.add(r.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);
            listViewRecipes.setAdapter(adapter);

            listViewRecipes.setOnItemClickListener((parent, view, position, id) -> {
                RecipeModel selected = matchedRecipes.get(position);
                Intent intent = new Intent(RecipeActivity.this, RecipeDetailActivity.class);
                intent.putExtra("RECIPE", selected);
                startActivity(intent);
            });
        }
    }
}