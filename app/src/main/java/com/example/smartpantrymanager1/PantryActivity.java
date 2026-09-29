package com.example.smartpantrymanager1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText etItemName, etQuantity;
    Button btnAdd;
    ListView listViewPantry;

    ArrayList<String> pantryList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        // Initialize database helper and UI elements
        databaseHelper = new DatabaseHelper(this);
        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        btnAdd = findViewById(R.id.btnAddItem);
        listViewPantry = findViewById(R.id.listViewPantry);

        // Load existing pantry items into the list view
        loadPantryData();

        // Button click to add item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etItemName.getText().toString().trim();
                String quantityStr = etQuantity.getText().toString().trim();

                if (!name.isEmpty() && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);

                    // Match the 4-parameter constructor (name, quantity, unit, dateAdded)
                    PantryItem item = new PantryItem(name, quantity, "pcs", "Today");

                    boolean success = databaseHelper.addPantryItem(item);

                    if (success) {
                        Toast.makeText(PantryActivity.this, "Item Added!", Toast.LENGTH_SHORT).show();
                        etItemName.setText("");
                        etQuantity.setText("");
                        loadPantryData(); // Refresh the list
                    } else {
                        Toast.makeText(PantryActivity.this, "Error adding item", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PantryActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadPantryData() {
        // Fetch all items as a List to match your DatabaseHelper return type
        List<PantryItem> items = databaseHelper.getAllPantryItems();
        pantryList = new ArrayList<>();

        for (PantryItem item : items) {
            pantryList.add(item.getName() + " - Qty: " + item.getQuantity() + " (" + item.getUnit() + ")");
        }

        // Set adapter to display items in the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pantryList);
        listViewPantry.setAdapter(adapter);
    }
}