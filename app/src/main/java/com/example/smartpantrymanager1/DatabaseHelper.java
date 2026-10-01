package com.example.smartpantrymanager1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;

    public static final String PANTRY_TABLE = "pantry";
    public static final String RECIPE_TABLE = "recipes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + PANTRY_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity TEXT NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiryDate TEXT)");

        db.execSQL("CREATE TABLE " + RECIPE_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "ingredients TEXT NOT NULL, " +
                "instructions TEXT NOT NULL)");

        addStarterRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PANTRY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE);

        onCreate(db);
    }

    // =========================
    // PANTRY
    // =========================

    public long addPantryItem(String name, String quantity,
                              String unit, String expiryDate) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiryDate", expiryDate);

        return db.insert(PANTRY_TABLE, null, values);
    }

    public ArrayList<PantryItem> getAllPantryItems() {

        ArrayList<PantryItem> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + PANTRY_TABLE + " ORDER BY name ASC",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                String quantity = cursor.getString(
                        cursor.getColumnIndexOrThrow("quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow("expiryDate")
                );

                items.add(
                        new PantryItem(
                                id,
                                name,
                                quantity,
                                unit,
                                expiryDate
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();

        return items;
    }

    public int updatePantryItem(int id, String name,
                                String quantity,
                                String unit,
                                String expiryDate) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiryDate", expiryDate);

        return db.update(
                PANTRY_TABLE,
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public int deletePantryItem(int id) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                PANTRY_TABLE,
                "id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    // =========================
    // RECIPES
    // =========================

    public ArrayList<RecipeModel> getAllRecipes() {

        ArrayList<RecipeModel> recipes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + RECIPE_TABLE + " ORDER BY name ASC",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                String ingredients = cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredients")
                );

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow("instructions")
                );

                recipes.add(
                        new RecipeModel(
                                id,
                                name,
                                ingredients,
                                instructions
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();

        return recipes;
    }

    public RecipeModel getRecipeById(int id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + RECIPE_TABLE +
                        " WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        RecipeModel recipe = null;

        if (cursor.moveToFirst()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            String ingredients = cursor.getString(
                    cursor.getColumnIndexOrThrow("ingredients")
            );

            String instructions = cursor.getString(
                    cursor.getColumnIndexOrThrow("instructions")
            );

            recipe = new RecipeModel(
                    id,
                    name,
                    ingredients,
                    instructions
            );
        }

        cursor.close();

        return recipe;
    }

    // =========================
    // STRICT RECIPE MATCHING
    // =========================

    public ArrayList<RecipeModel> getStrictMatchedRecipes() {

        ArrayList<RecipeModel> matchedRecipes =
                new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<String> pantryIngredients =
                new ArrayList<>();

        Cursor pantryCursor = db.rawQuery(
                "SELECT name FROM " + PANTRY_TABLE,
                null
        );

        if (pantryCursor.moveToFirst()) {

            do {

                String ingredient =
                        pantryCursor.getString(
                                pantryCursor.getColumnIndexOrThrow("name")
                        );

                if (ingredient != null) {

                    pantryIngredients.add(
                            cleanIngredient(ingredient)
                    );
                }

            } while (pantryCursor.moveToNext());
        }

        pantryCursor.close();

        Cursor recipeCursor = db.rawQuery(
                "SELECT * FROM " + RECIPE_TABLE,
                null
        );

        if (recipeCursor.moveToFirst()) {

            do {

                int id = recipeCursor.getInt(
                        recipeCursor.getColumnIndexOrThrow("id")
                );

                String name = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow("name")
                );

                String ingredients =
                        recipeCursor.getString(
                                recipeCursor.getColumnIndexOrThrow(
                                        "ingredients"
                                )
                        );

                String instructions =
                        recipeCursor.getString(
                                recipeCursor.getColumnIndexOrThrow(
                                        "instructions"
                                )
                        );

                String[] requiredIngredients =
                        ingredients.split(",");

                boolean canMakeRecipe = true;

                for (String requiredIngredient :
                        requiredIngredients) {

                    String required =
                            cleanIngredient(requiredIngredient);

                    if (!pantryIngredients.contains(required)) {

                        canMakeRecipe = false;
                        break;
                    }
                }

                if (canMakeRecipe) {

                    matchedRecipes.add(
                            new RecipeModel(
                                    id,
                                    name,
                                    ingredients,
                                    instructions
                            )
                    );
                }

            } while (recipeCursor.moveToNext());
        }

        recipeCursor.close();

        return matchedRecipes;
    }

    private String cleanIngredient(String ingredient) {

        if (ingredient == null) {
            return "";
        }

        return ingredient
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", " ");
    }

    // =========================
    // STARTER RECIPES
    // =========================

    private void addStarterRecipes(SQLiteDatabase db) {

        addRecipe(db, "Egg Fried Rice",
                "rice,egg,onion,oil",
                "Cook the rice. Fry the onion in oil. Add the egg and cooked rice. Stir and serve.");

        addRecipe(db, "Vegetable Rice",
                "rice,carrot,onion,tomato",
                "Cook the rice. Fry the vegetables and mix them with the cooked rice.");

        addRecipe(db, "Tomato Pasta",
                "pasta,tomato,onion,oil",
                "Cook the pasta. Fry the onion and tomato in oil. Mix with the pasta.");

        addRecipe(db, "Cheese Omelette",
                "egg,cheese,oil",
                "Beat the eggs. Add cheese and cook in oil until the omelette is ready.");

        addRecipe(db, "Chicken Rice",
                "chicken,rice,onion,oil",
                "Cook the rice. Cook the chicken with onion and oil. Mix together.");

        addRecipe(db, "Chicken Sandwich",
                "bread,chicken,tomato,onion",
                "Prepare the chicken. Add chicken, tomato and onion between slices of bread.");

        addRecipe(db, "Egg Sandwich",
                "bread,egg,onion",
                "Cook the egg. Add egg and onion between slices of bread.");

        addRecipe(db, "Potato Omelette",
                "potato,egg,onion,oil",
                "Cook the potato and onion. Add beaten eggs and cook until firm.");

        addRecipe(db, "Chicken Pasta",
                "pasta,chicken,onion,tomato",
                "Cook the pasta. Cook chicken with onion and tomato. Mix with pasta.");

        addRecipe(db, "Vegetable Omelette",
                "egg,tomato,onion,carrot,oil",
                "Fry the vegetables. Add beaten eggs and cook until firm.");

        addRecipe(db, "Tomato Egg Rice",
                "rice,egg,tomato,onion",
                "Cook the rice. Fry tomato and onion. Add egg and rice and stir.");

        addRecipe(db, "Chicken Tomato Pasta",
                "pasta,chicken,tomato,oil",
                "Cook the pasta. Cook chicken and tomato in oil. Mix together.");

        addRecipe(db, "Cheesy Pasta",
                "pasta,cheese,milk",
                "Cook the pasta. Heat milk and cheese until melted. Mix with pasta.");

        addRecipe(db, "Potato and Chicken",
                "potato,chicken,onion,oil",
                "Cook the potatoes. Fry chicken and onion. Add potatoes and cook together.");

        addRecipe(db, "Simple Vegetable Pasta",
                "pasta,carrot,onion,tomato",
                "Cook the pasta. Fry the vegetables and mix with pasta.");

        addRecipe(db, "Egg and Tomato",
                "egg,tomato,onion,oil",
                "Fry onion and tomato. Add beaten eggs and cook until ready.");

        addRecipe(db, "Chicken Omelette",
                "egg,chicken,onion,oil",
                "Cook chicken and onion. Add beaten eggs and cook until firm.");

        addRecipe(db, "Cheese Sandwich",
                "bread,cheese,tomato",
                "Place cheese and tomato between slices of bread.");

        addRecipe(db, "Potato and Egg",
                "potato,egg,onion",
                "Cook potato and onion. Add beaten egg and cook until done.");

        addRecipe(db, "Chicken Vegetable Rice",
                "chicken,rice,carrot,onion",
                "Cook rice. Cook chicken with carrot and onion. Mix together.");
    }

    private void addRecipe(SQLiteDatabase db,
                           String name,
                           String ingredients,
                           String instructions) {

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("instructions", instructions);

        db.insert(RECIPE_TABLE, null, values);
    }
}