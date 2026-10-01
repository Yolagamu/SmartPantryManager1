package com.example.smartpantrymanager1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private EditText etItemName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private Button btnAddPantry;
    private ListView listViewPantry;

    private DatabaseHelper databaseHelper;

    private ArrayList<PantryItem> pantryItems;
    private ArrayList<String> pantryDisplayList;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry);

        databaseHelper = new DatabaseHelper(this);

        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        btnAddPantry = findViewById(R.id.btnAddPantry);
        listViewPantry = findViewById(R.id.listViewPantry);

        btnAddPantry.setOnClickListener(view -> addPantryItem());

        listViewPantry.setOnItemClickListener(
                (parent, view, position, id) -> showItemOptions(position)
        );

        loadPantryItems();
    }

    private void addPantryItem() {

        String name = etItemName.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            etItemName.setError("Enter an ingredient name");
            return;
        }

        if (quantity.isEmpty()) {
            etQuantity.setError("Enter a quantity");
            return;
        }

        if (unit.isEmpty()) {
            etUnit.setError("Enter a unit");
            return;
        }

        long result = databaseHelper.addPantryItem(
                name,
                quantity,
                unit,
                expiryDate
        );

        if (result != -1) {

            Toast.makeText(
                    this,
                    "Ingredient added to pantry",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();
            loadPantryItems();

        } else {

            Toast.makeText(
                    this,
                    "Could not add ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void loadPantryItems() {

        pantryItems = databaseHelper.getAllPantryItems();

        pantryDisplayList = new ArrayList<>();

        for (PantryItem item : pantryItems) {

            String displayText =
                    item.getName()
                            + " - "
                            + item.getQuantity()
                            + " "
                            + item.getUnit();

            if (item.getExpiryDate() != null
                    && !item.getExpiryDate().isEmpty()) {

                displayText +=
                        "\nExpiry: "
                                + item.getExpiryDate();
            }

            pantryDisplayList.add(displayText);
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                pantryDisplayList
        );

        listViewPantry.setAdapter(adapter);
    }

    private void showItemOptions(int position) {

        PantryItem selectedItem =
                pantryItems.get(position);

        String[] options = {
                "Edit",
                "Delete"
        };

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle(selectedItem.getName());

        builder.setItems(
                options,
                (dialog, which) -> {

                    if (which == 0) {
                        showEditDialog(selectedItem);
                    } else if (which == 1) {
                        showDeleteDialog(selectedItem);
                    }
                }
        );

        builder.show();
    }

    private void showEditDialog(PantryItem item) {

        View dialogView =
                LayoutInflater.from(this)
                        .inflate(
                                R.layout.dialog_edit_pantry,
                                null
                        );

        EditText editName =
                dialogView.findViewById(
                        R.id.editName
                );

        EditText editQuantity =
                dialogView.findViewById(
                        R.id.editQuantity
                );

        EditText editUnit =
                dialogView.findViewById(
                        R.id.editUnit
                );

        EditText editExpiry =
                dialogView.findViewById(
                        R.id.editExpiry
                );

        editName.setText(item.getName());
        editQuantity.setText(item.getQuantity());
        editUnit.setText(item.getUnit());
        editExpiry.setText(item.getExpiryDate());

        AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("Edit Pantry Item")
                        .setView(dialogView)
                        .setPositiveButton(
                                "Save",
                                null
                        )
                        .setNegativeButton(
                                "Cancel",
                                null
                        )
                        .create();

        dialog.setOnShowListener(
                dialogInterface -> {

                    Button saveButton =
                            dialog.getButton(
                                    AlertDialog.BUTTON_POSITIVE
                            );

                    saveButton.setOnClickListener(
                            view -> {

                                String name =
                                        editName.getText()
                                                .toString()
                                                .trim();

                                String quantity =
                                        editQuantity.getText()
                                                .toString()
                                                .trim();

                                String unit =
                                        editUnit.getText()
                                                .toString()
                                                .trim();

                                String expiry =
                                        editExpiry.getText()
                                                .toString()
                                                .trim();

                                if (name.isEmpty()) {
                                    editName.setError(
                                            "Enter an ingredient name"
                                    );
                                    return;
                                }

                                if (quantity.isEmpty()) {
                                    editQuantity.setError(
                                            "Enter a quantity"
                                    );
                                    return;
                                }

                                if (unit.isEmpty()) {
                                    editUnit.setError(
                                            "Enter a unit"
                                    );
                                    return;
                                }

                                databaseHelper.updatePantryItem(
                                        item.getId(),
                                        name,
                                        quantity,
                                        unit,
                                        expiry
                                );

                                Toast.makeText(
                                        this,
                                        "Pantry item updated",
                                        Toast.LENGTH_SHORT
                                ).show();

                                dialog.dismiss();

                                loadPantryItems();
                            }
                    );
                }
        );

        dialog.show();
    }

    private void showDeleteDialog(PantryItem item) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage(
                        "Are you sure you want to delete "
                                + item.getName()
                                + "?"
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            databaseHelper.deletePantryItem(
                                    item.getId()
                            );

                            Toast.makeText(
                                    this,
                                    "Item deleted",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadPantryItems();
                        }
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .show();
    }

    private void clearFields() {

        etItemName.setText("");
        etQuantity.setText("");
        etUnit.setText("");
        etExpiryDate.setText("");

        etItemName.requestFocus();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (databaseHelper != null) {
            loadPantryItems();
        }
    }
}
