#Smart Pantry Manager

About the Project

The Smart Pantry Manager is a Java Android application that was developed for the Mobile App Development practical task 700.

The objective of the application is to assist users to keep track of the products they have stored in their pantry and find dishes that can be made with the available items. This will help in minimizing food waste by using the available pantry items and leftovers.

Functions
1. The management of pantry

Users can manage the items present in their pantry.

The application allows users to:

* Add items present in the pantry.
* Input the number and units.
* Add an optional expiry date.
* View the pantry items.
* Edit the pantry items. 
* Remove the pantry items.
  
About the Project

Smart Pantry Manager is a Java Android application developed for the Mobile App Development practicum 700.

The aim of the application is to support its users in tracking what they have kept in their pantry and in discovering recipes from stored products. This in turn aids in the reduction of food waste by utilizing the existing pantry stocks.

Functions
1. Managing the pantry.
 
The users are able to control everything existing in their pantry.
 
The app makes it possible for the users to:
 
1. Add products in the pantry.
2. Enter quantity and measurements.
3. Input optional expiry date.
4. Check the contents of the pantry.
5. Change the items in the pantry.
6. Delete products from the pantry.

The application also uses:

DatabaseHelper for SQLite database operations
PantryItem for pantry item data
RecipeModel for recipe data
How the Application Works
The user opens Smart Pantry Manager.
The user adds ingredients that are currently available in their pantry.
Pantry information is saved in the SQLite database.
The user opens Suggested Recipes.
The application compares the pantry ingredients with the required ingredients for each recipe.
Recipes are displayed only when all required ingredients are available.
The user can select a suggested recipe to view its ingredients and preparation instructions.
Example

If the pantry contains:

- Rice
- Egg
- Onion
- Oil

#the application can suggest:

##Egg Fried Rice

However, if the pantry does not contain one of the required ingredients, the recipe will not be suggested.

Project Purpose

This project demonstrates the use of Android development concepts including:

* Activities
* Activity navigation
* XML layouts
* Intents
* SQLite database storage
* User input
* ListViews
* Dialogs
* Persistent data
* Basic application logic

  
Assignment

- Module: Mobile App Development 700

- Project: Smart Pantry Manager

- Platform: Android

- Language: Java

- Development Environment: Android Studio

Author

Yolanda Munyengwa
