
package com.example.smartpantrymanager1;

public class Recipe {
    private int id;
    private String title;
    private String ingredients;
    private String instructions;

    // Constructor for creating a new recipe
    public Recipe(String title, String ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Constructor for pulling an existing recipe from the SQLite database
    public Recipe(int id, String title, String ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}