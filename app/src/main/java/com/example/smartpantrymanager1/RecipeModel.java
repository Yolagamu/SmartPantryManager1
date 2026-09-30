package com.example.smartpantrymanager1;

import java.io.Serializable;

public class RecipeModel implements Serializable {
    private String name;
    private String ingredients;
    private String steps;

    public RecipeModel(String name, String ingredients, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }
}