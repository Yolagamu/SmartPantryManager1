package com.example.smartpantrymanager1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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
        btnAddPantry = findViewById(R.id.btnAddItem);
        listViewPantry = findViewById(R.id.listViewPantry);


        loadPantryList();


        btnAddPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etItemName.getText().toString().trim();
                String qtyStr = etQuantity.getText().toString().trim();

                // --- INPUT VALIDATION ---
                if (name.isEmpty()) {
                    etItemName.setText("");
                    Toast.makeText(PantryActivity.this, "Please enter an item name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (qtyStr.isEmpty()) {
                    Toast.makeText(PantryActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity = Integer.parseInt(qtyStr);

                // Insert into database
                boolean inserted = databaseHelper.addPantryItem(name, quantity);
                if (inserted) {
                    Toast.makeText(PantryActivity.this, "Item Added!", Toast.LENGTH_SHORT).show();
                    etItemName.setText("");
                    etQuantity.setText("");
                    loadPantryList(); // Refresh the list view
                } else {
                    Toast.makeText(PantryActivity.this, "Error adding item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void loadPantryList() {
        Cursor cursor = databaseHelper.getAllPantryItems();

        String[] fromColumns = {DatabaseHelper.COLUMN_PANTRY_NAME, DatabaseHelper.COLUMN_PANTRY_QTY};
        int[] toViews = {android.R.id.text1, android.R.id.text2}; // Standard list layout fields
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