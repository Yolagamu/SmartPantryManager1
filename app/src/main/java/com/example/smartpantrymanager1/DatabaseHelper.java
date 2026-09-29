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

    // Pantry Table
    private static final String TABLE_PANTRY = "pantry_table";
    private static final String COL_PANTRY_ID = "id";
    private static final String COL_PANTRY_NAME = "name";
    private static final String COL_PANTRY_QTY = "quantity";
    private static final String COL_PANTRY_UNIT = "unit";
    private static final String COL_PANTRY_DATE = "date_added";

    // Recipe Table
    private static final String TABLE_RECIPE = "recipe_table";
    private static final String COL_RECIPE_ID = "id";
    private static final String COL_RECIPE_TITLE = "title";
    private static final String COL_RECIPE_ING = "ingredients";
    private static final String COL_RECIPE_INST = "instructions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PANTRY_NAME + " TEXT, " +
                COL_PANTRY_QTY + " INTEGER, " +
                COL_PANTRY_UNIT + " TEXT, " +
                COL_PANTRY_DATE + " TEXT)";

        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPE + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_TITLE + " TEXT, " +
                COL_RECIPE_ING + " TEXT, " +
                COL_RECIPE_INST + " TEXT)";

        db.execSQL(createPantryTable);
        db.execSQL(createRecipeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(db);
    }

    // --- PANTRY CRUD METHODS ---

    public boolean addPantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PANTRY_NAME, item.getName());
        cv.put(COL_PANTRY_QTY, item.getQuantity());
        cv.put(COL_PANTRY_UNIT, item.getUnit());
        cv.put(COL_PANTRY_DATE, item.getDateAdded());

        long result = db.insert(TABLE_PANTRY, null, cv);
        return result != -1;
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> itemList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PANTRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String unit = cursor.getString(3);
                String date = cursor.getString(4);

                PantryItem item = new PantryItem(id, name, quantity, unit, date);
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    // --- RECIPE CRUD METHODS ---

    public boolean addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_RECIPE_TITLE, recipe.getTitle());
        cv.put(COL_RECIPE_ING, recipe.getIngredients());
        cv.put(COL_RECIPE_INST, recipe.getInstructions());

        long result = db.insert(TABLE_RECIPE, null, cv);
        return result != -1;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_RECIPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String ingredients = cursor.getString(2);
                String instructions = cursor.getString(3);

                Recipe recipe = new Recipe(id, title, ingredients, instructions);
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipeList;
    }
}
