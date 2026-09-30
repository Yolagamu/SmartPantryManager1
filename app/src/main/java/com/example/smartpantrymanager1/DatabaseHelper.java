package com.example.smartpantrymanager1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_PANTRY = "pantry";
    public static final String TABLE_RECIPES = "recipes";

    // Pantry Columns (Note: _id is required for SimpleCursorAdapter)
    public static final String COLUMN_PANTRY_ID = "id";
    public static final String COLUMN_PANTRY_NAME = "name";
    public static final String COLUMN_PANTRY_QTY = "quantity";

    // Recipe Columns
    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_RECIPE_INGREDIENTS = "ingredients";
    public static final String COLUMN_RECIPE_STEPS = "steps";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Pantry Table
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COLUMN_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PANTRY_NAME + " TEXT, " +
                COLUMN_PANTRY_QTY + " INTEGER)";
        db.execSQL(createPantryTable);

        // Create Recipes Table
        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT, " +
                COLUMN_RECIPE_INGREDIENTS + " TEXT, " +
                COLUMN_RECIPE_STEPS + " TEXT)";
        db.execSQL(createRecipeTable);

        // Seed 15 Recipes for assignment requirement
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    // --- CRUD OPERATIONS FOR PANTRY ---

    public boolean addPantryItem(String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PANTRY_NAME, name.toLowerCase().trim());
        values.put(COLUMN_PANTRY_QTY, quantity);
        long result = db.insert(TABLE_PANTRY, null, values);
        return result != -1;
    }

    // CRITICAL: Aliasing id as _id prevents SimpleCursorAdapter crashes
    public Cursor getAllPantryItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, name, quantity FROM " + TABLE_PANTRY, null);
    }

    public boolean updatePantryItem(int id, String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PANTRY_NAME, name.toLowerCase().trim());
        values.put(COLUMN_PANTRY_QTY, quantity);
        int rows = db.update(TABLE_PANTRY, values, COLUMN_PANTRY_ID + " = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public void deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, COLUMN_PANTRY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // --- SEEDING 15 RECIPES ---
    private void seedRecipes(SQLiteDatabase db) {
        String[][] recipes = {
                {"Pasta Bolognese", "pasta, tomato, ground beef", "Boil pasta. Cook beef, mix with tomato sauce."},
                {"Cheese Omelet", "eggs, cheese, butter", "Whisk eggs, fry in butter, fold with cheese."},
                {"Vegetable Stir Fry", "mixed veggies, soy sauce, rice", "Stir-fry veggies, add soy sauce, serve over rice."},
                {"Tuna Sandwich", "bread, tuna, mayonnaise", "Mix tuna and mayo, spread between bread slices."},
                {"Garlic Butter Rice", "rice, garlic, butter", "Melt butter, sauté minced garlic, toss with cooked rice."},
                {"Scrambled Eggs", "eggs, butter, salt", "Whisk eggs with salt, scramble in a pan with butter."},
                {"Tomato Soup", "tomato, garlic, onion", "Blend cooked tomatoes, onion, and garlic, then simmer."},
                {"Pancakes", "flour, milk, eggs", "Mix ingredients into a batter and cook on a hot griddle."},
                {"Grilled Cheese", "bread, cheese, butter", "Butter bread slices, put cheese inside, grill in a pan."},
                {"Simple Salad", "lettuce, tomato, cucumber", "Chop veggies and toss together with dressing."},
                {"Fried Rice", "rice, eggs, soy sauce", "Fry cooked rice with scrambled eggs and soy sauce."},
                {"Boiled Eggs", "eggs, water", "Boil water, cook eggs for 10 minutes, peel and serve."},
                {"Butter Toast", "bread, butter", "Toast bread and spread butter evenly."},
                {"Black Coffee", "coffee beans, water", "Brew coffee grounds with hot water."},
                {"Plain Oatmeal", "oats, milk", "Simmer oats in milk until soft and creamy."}
        };

        for (String[] r : recipes) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_RECIPE_NAME, r[0]);
            cv.put(COLUMN_RECIPE_INGREDIENTS, r[1]);
            cv.put(COLUMN_RECIPE_STEPS, r[2]);
            db.insert(TABLE_RECIPES, null, cv);
        }
    }

    // --- CORE STRICT-MATCHING LOGIC ---
    public List<String> getStrictMatchedRecipes() {
        List<String> matchedRecipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        List<String> userPantry = new ArrayList<>();
        Cursor cursorPantry = db.rawQuery("SELECT " + COLUMN_PANTRY_NAME + " FROM " + TABLE_PANTRY, null);
        if (cursorPantry.moveToFirst()) {
            do {
                userPantry.add(cursorPantry.getString(0).toLowerCase().trim());
            } while (cursorPantry.moveToNext());
        }
        cursorPantry.close();

        Cursor cursorRecipes = db.rawQuery("SELECT * FROM " + TABLE_RECIPES, null);
        if (cursorRecipes.moveToFirst()) {
            do {
                String recipeName = cursorRecipes.getString(cursorRecipes.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));
                String ingredientsStr = cursorRecipes.getString(cursorRecipes.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENTS));

                String[] requiredIngredients = ingredientsStr.split(",");
                boolean canMake = true;

                for (String ing : requiredIngredients) {
                    String trimmedIng = ing.toLowerCase().trim();
                    if (!userPantry.contains(trimmedIng)) {
                        canMake = false;
                        break;
                    }
                }

                if (canMake && requiredIngredients.length > 0 && !userPantry.isEmpty()) {
                    matchedRecipes.add(recipeName + " - Uses: " + ingredientsStr);
                }

            } while (cursorRecipes.moveToNext());
        }
        cursorRecipes.close();

        return matchedRecipes;
    }
}