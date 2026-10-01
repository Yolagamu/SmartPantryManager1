package com.example.smartpantrymanager1;

import java.io.Serializable;

public class RecipeModel implements Serializable {

    private int id;
    private String name;
    private String ingredients;
    private String instructions;

    public RecipeModel(int id, String name, String ingredients,
                       String instructions) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}