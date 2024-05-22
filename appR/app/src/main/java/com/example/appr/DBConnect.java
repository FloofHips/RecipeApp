package com.example.appr;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBConnect extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 2;
        private static final String DATABASE_NAME = "RecipesApp";
        private static final String TABLE_USER = "user";
        private static final String TABLE_RECIPE = "recipe";

        public DBConnect(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
                String CREATE_USER_TABLE = "CREATE TABLE user (" +
                        "idUser INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nameUser VARCHAR(255) NOT NULL, " +
                        "pwUser VARCHAR(255) NOT NULL);"; // Ensure pwUser is NOT NULL
                db.execSQL(CREATE_USER_TABLE);

                String CREATE_RECIPE_TABLE = "CREATE TABLE recipe (" +
                        "idRec INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "idUserRec INTEGER, " +
                        "nameRec VARCHAR(255) NOT NULL, " +
                        "timeRec INTEGER, " +
                        "catRec VARCHAR(255), " +
                        "ingRec VARCHAR(255), " +
                        "instrRec VARCHAR(255), " +
                        "FOREIGN KEY (idUserRec) REFERENCES user(idUser) ON DELETE CASCADE);"; // Foreign key constraint
                db.execSQL(CREATE_RECIPE_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // Drop older table if existed
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);

                // Create tables again
                onCreate(db);
        }

        // code to add the new contact
        void addContact(UserActivity user1) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("nameUser", user1.getName());
                values.put("pwUser", user1.getPw());

                // Inserting Row
                db.insert(TABLE_USER, null, values);
                db.close(); // Closing database connection
        }

        // code to get the single contact
        public UserActivity getUser(int id) {
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(TABLE_USER, new String[]{"idUser", "nameUser", "pwUser"},
                        "idUser=?", new String[]{String.valueOf(id)}, null, null, null);

                UserActivity user1 = null;

                try {
                        if (cursor != null && cursor.moveToFirst()) {
                                user1 = new UserActivity(Integer.parseInt(cursor.getString(0)),
                                        cursor.getString(1), cursor.getString(2));
                        }
                } finally {
                        // Close cursor to release resources
                        if (cursor != null) {
                                cursor.close();
                        }
                }

                return user1;
        }





        @SuppressLint("Range")
        public String getName(int userId) {
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(TABLE_USER, new String[]{"nameUser"},
                        "idUser=?", new String[]{String.valueOf(userId)}, null, null, null);

                try {
                        if (cursor != null && cursor.moveToFirst()) {
                                return cursor.getString(cursor.getColumnIndex("nameUser"));
                        }
                } finally {
                        // Close cursor to release resources
                        if (cursor != null) {
                                cursor.close();
                        }
                }

                return ""; // Return an empty string if no username found for the userId
        }


        public int validCred(String name, String password) {
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = null;
                int userId = -1;

                try {
                        // Query the database to find the user with the given name
                        cursor = db.query(TABLE_USER, new String[]{"idUser", "pwUser"}, "nameUser=?", new String[]{name}, null, null, null);

                        if (cursor != null && cursor.moveToFirst()) {
                                // If the cursor is not null and move to first is successful, retrieve the user ID and password
                                @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex("pwUser"));
                                @SuppressLint("Range") int storedUserId = cursor.getInt(cursor.getColumnIndex("idUser"));

                                // Check if the stored password matches the provided password
                                if (storedPassword.equals(password)) {
                                        userId = storedUserId; // Set userId to storedUserId if passwords match
                                }
                        }
                } finally {
                        // Close cursor to release resources
                        if (cursor != null) {
                                cursor.close();
                        }
                }

                return userId;
        }






/******************************************************************************************/

        // code to add  new recipe
        public int addRecipe(recipeActivity rec1) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("idUserRec", rec1.getidUser());
                values.put("nameRec", rec1.getName());
                values.put("timeRec", rec1.getTime());
                values.put("catRec", rec1.getCategories());
                values.put("ingRec", rec1.getIngredients());
                values.put("instrRec", rec1.getInstru());


                db.insert(TABLE_RECIPE, null, values);
                db.close(); // Closing database connection
                return rec1.getid();
        }

        // // modifier recette
        public int updateRecipe(recipeActivity rec) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("idUserRec", rec.getidUser());
                values.put("nameRec", rec.getName());
                values.put("timeRec", rec.getTime());
                values.put("catRec", rec.getCategories());
                values.put("ingRec", rec.getIngredients());
                values.put("instrRec", rec.getInstru());

                // Log the values before updating
                Log.d("Recipe Update", "Name: " + rec.getName()); // Check if this prints the updated value
                Log.d("Recipe Update", "Query: " + db.compileStatement("UPDATE " + TABLE_RECIPE +
                        " SET idUserRec = ?, nameRec = ?, timeRec = ?, catRec = ?, ingRec = ?, instrRec = ?" +
                        " WHERE idRec = ?").toString());

                // updating row
                return db.update(TABLE_RECIPE, values, "idRec" + " = ?",
                        new String[] { String.valueOf(rec.getid()) });
        }



        // Deleting single recipe , supprimer single recette
        public void deleteRecipe(recipeActivity rec1) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.delete(TABLE_USER, "idRec" + " = ?",
                        new String[] { String.valueOf(rec1.getid()) });
                db.close();
        }

        // Getting recipes Count
        public int getRecipeCount() {
                String countQuery = "SELECT  * FROM " + TABLE_RECIPE;
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(countQuery, null);
                cursor.close();

                // return count
                return cursor.getCount();
        }

        // code to get the single recette
        // code to get the single recette
        public recipeActivity getRecipe(int id) {
                SQLiteDatabase db = this.getReadableDatabase();
                recipeActivity rec = null;
                Cursor cursor = null;

                try {
                        // Query the database to retrieve the recipe with the given ID
                        cursor = db.query(TABLE_RECIPE, new String[]{"idRec", "idUserRec", "nameRec", "timeRec", "catRec", "ingRec", "instrRec"},
                                "idRec=?", new String[]{String.valueOf(id)}, null, null, null);

                        // Check if the cursor is not null and move to first is successful
                        if (cursor != null && cursor.moveToFirst()) {
                                // Extract values from the cursor
                                @SuppressLint("Range") int idRec = cursor.getInt(cursor.getColumnIndex("idRec"));
                                @SuppressLint("Range") int idUserRec = cursor.getInt(cursor.getColumnIndex("idUserRec"));
                                @SuppressLint("Range") String nameRec = cursor.getString(cursor.getColumnIndex("nameRec"));
                                @SuppressLint("Range") int timeRec = cursor.getInt(cursor.getColumnIndex("timeRec"));
                                @SuppressLint("Range") String catRec = cursor.getString(cursor.getColumnIndex("catRec"));
                                @SuppressLint("Range") String ingRec = cursor.getString(cursor.getColumnIndex("ingRec"));
                                @SuppressLint("Range") String instrRec = cursor.getString(cursor.getColumnIndex("instrRec"));

                                // Create a new recipe object with the retrieved values
                                rec = new recipeActivity(idRec, Integer.parseInt(String.valueOf(idUserRec)), nameRec, timeRec, catRec, ingRec, instrRec);
                        }
                } finally {
                        // Close the cursor to release resources
                        if (cursor != null) {
                                cursor.close();
                        }
                }

                return rec;
        }



        // code to get all recipes in a list view
        public List<recipeActivity> getAllRec() {
                List<recipeActivity> recipeList = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = null;

                try {
                        // Select all rows from the recipes table
                        cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE, null);

                        // Check if the cursor is not null and move to first is successful
                        if (cursor != null && cursor.moveToFirst()) {
                                do {
                                        // Extract values from the cursor
                                        @SuppressLint("Range") int idRec = cursor.getInt(cursor.getColumnIndex("idRec"));
                                        @SuppressLint("Range") int idUserRec = cursor.getInt(cursor.getColumnIndex("idUserRec"));
                                        @SuppressLint("Range") String nameRec = cursor.getString(cursor.getColumnIndex("nameRec"));
                                        @SuppressLint("Range") int timeRec = cursor.getInt(cursor.getColumnIndex("timeRec"));
                                        @SuppressLint("Range") String catRec = cursor.getString(cursor.getColumnIndex("catRec"));
                                        @SuppressLint("Range") String ingRec = cursor.getString(cursor.getColumnIndex("ingRec"));
                                        @SuppressLint("Range") String instrRec = cursor.getString(cursor.getColumnIndex("instrRec"));

                                        // Create a new recipe object with the retrieved values
                                        recipeActivity rec = new recipeActivity(idRec, Integer.parseInt(String.valueOf(idUserRec)), nameRec, timeRec, catRec, ingRec, instrRec);

                                        // Add the recipe object to the list
                                        recipeList.add(rec);
                                } while (cursor.moveToNext());
                        }
                } finally {
                        // Close the cursor to release resources
                        if (cursor != null) {
                                cursor.close();
                        }
                }

                // Return the list of recipes
                return recipeList;
        }

}