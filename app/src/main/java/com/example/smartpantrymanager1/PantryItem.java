package com.example.smartpantrymanager1;

public class PantryItem {
    private int id;
    private String name;
    private int quantity;
    private String unit;
    private String dateAdded;

    //For new items without ID
    public PantryItem(String name, int quantity, String unit, String dateAdded) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.dateAdded = dateAdded;
    }

    // Constructor for pulling an existing item from the SQLite database
    public PantryItem(int id, String name, int quantity, String unit, String dateAdded) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.dateAdded = dateAdded;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }
}
