package com.example.smartpantrymanager1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PantryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText etItemName, etQuantity;
    Button btnAddPantry;
    ListView listViewPantry;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        databaseHelper = new DatabaseHelper(this);

        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        btnAddPantry = findViewById(R.id.btnAddPantry);
        listViewPantry = findViewById(R.id.listViewPantry);

        loadPantryList();

        btnAddPantry.setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            String qtyStr = etQuantity.getText().toString().trim();

            // Input Validation
            if (name.isEmpty()) {
                Toast.makeText(PantryActivity.this, "Please enter an item name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (qtyStr.isEmpty()) {
                Toast.makeText(PantryActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(qtyStr);
            boolean inserted = databaseHelper.addPantryItem(name, quantity);

            if (inserted) {
                Toast.makeText(PantryActivity.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                etItemName.setText("");
                etQuantity.setText("");
                loadPantryList();
            } else {
                Toast.makeText(PantryActivity.this, "Error adding item", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete item on long click
        listViewPantry.setOnItemLongClickListener((parent, view, position, id) -> {
            databaseHelper.deletePantryItem(id);
            Toast.makeText(PantryActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            loadPantryList();
            return true;
        });
    }

    private void loadPantryList() {
        Cursor cursor = databaseHelper.getAllPantryItems();
        String[] fromColumns = {DatabaseHelper.COLUMN_PANTRY_NAME, DatabaseHelper.COLUMN_PANTRY_QTY};
        int[] toViews = {android.R.id.text1, android.R.id.text2};

        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                fromColumns,
                toViews,
                0
        );
        listViewPantry.setAdapter(cursorAdapter);
    }
}