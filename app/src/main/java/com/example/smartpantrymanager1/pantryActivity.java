package com.example.smartpantrymanager1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class PantryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText etItemName, etQuantity;
    Button btnAdd;
    ListView listViewPantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        //we will create activity_pantry.xml next
        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        btnAdd = findViewById(R.id.btnAddItem);
        listViewPantry = findViewById(R.id.listViewPantry);

        // Button click to add item
        btnAdd.setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();

            if (!name.isEmpty() && !quantityStr.isEmpty()) {
                int quantity = Integer.parseInt(quantityStr);
                boolean success = databaseHelper.addPantryItem(new PantryItem(name, quantity, "General"));
                if (success) {
                    // Item added successfully
                    etItemName.setText("");
                    etQuantity.setText("");
                }
            }
        });
    }
}
