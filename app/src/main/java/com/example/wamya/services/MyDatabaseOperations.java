package com.example.wamya.services;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wamya.models.Annonce;
import com.example.wamya.models.Appointement;
import com.example.wamya.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDatabaseOperations {

    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    // Constructor
    public MyDatabaseOperations(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    // Open the database
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database
    public void close() {
        dbHelper.close();
    }

    // Insert a user into the database
    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(MyDatabaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(MyDatabaseHelper.COLUMN_IS_BLOCKED, user.isBlocked() ? 1 : 0);
        values.put(MyDatabaseHelper.COLUMN_ADDRESS, user.getAddress());
        values.put(MyDatabaseHelper.COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        values.put(MyDatabaseHelper.COLUMN_ROLE, user.getRole().name());

        return database.insert(MyDatabaseHelper.TABLE_USERS, null, values);
    }

    // Query all users from the database
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USERS, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userList;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_USERNAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_PASSWORD)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_EMAIL)));
        user.setBlocked(cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_IS_BLOCKED)) == 1);
        user.setAddress(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ADDRESS)));
        user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_PHONE_NUMBER)));
        String roleString = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ROLE));
        User.UserRole role = (roleString != null) ? User.UserRole.valueOf(roleString) : User.UserRole.USER;
        user.setRole(role);

        return user;
    }


    public int updateUserById(int id, User user) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(MyDatabaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(MyDatabaseHelper.COLUMN_IS_BLOCKED, user.isBlocked() ? 1 : 0);
        values.put(MyDatabaseHelper.COLUMN_ADDRESS, user.getAddress());
        values.put(MyDatabaseHelper.COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        values.put(MyDatabaseHelper.COLUMN_ROLE, user.getRole().name());

        String whereClause = MyDatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        return database.update(MyDatabaseHelper.TABLE_USERS, values, whereClause, whereArgs);
    }

    // MyDatabaseOperations.java

    public Cursor getUser(String username, String password) {
        String[] columns = {
                MyDatabaseHelper.COLUMN_ID,
                MyDatabaseHelper.COLUMN_USERNAME,
                MyDatabaseHelper.COLUMN_PASSWORD,
                MyDatabaseHelper.COLUMN_ROLE  // Add the role column here
        };

        String selection = MyDatabaseHelper.COLUMN_USERNAME + " = ? AND " +
                MyDatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        return database.query(MyDatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
    }


    public User getUserById(int id) {
        User user = null;
        String[] columns = {
                MyDatabaseHelper.COLUMN_ID,
                MyDatabaseHelper.COLUMN_USERNAME,
                MyDatabaseHelper.COLUMN_PASSWORD,
                MyDatabaseHelper.COLUMN_EMAIL,
                MyDatabaseHelper.COLUMN_IS_BLOCKED,
                MyDatabaseHelper.COLUMN_ADDRESS,
                MyDatabaseHelper.COLUMN_PHONE_NUMBER,
                MyDatabaseHelper.COLUMN_ROLE
        };

        String selection = MyDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query(MyDatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ID));
            String username = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_EMAIL));
            boolean isBlocked = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_IS_BLOCKED)) == 1;
            String address = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ADDRESS));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_PHONE_NUMBER));
            String roleString = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ROLE));
            User.UserRole role = (roleString != null) ? User.UserRole.valueOf(roleString) : User.UserRole.USER;

            user = new User(userId, username);
            user.setPassword(password);
            user.setEmail(email);
            user.setBlocked(isBlocked);
            user.setAddress(address);
            user.setPhoneNumber(phoneNumber);
            user.setRole(role);
        }
        cursor.close();
        return user;
    }



    public long insertAnnonce(Annonce annonce) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_TITLE, annonce.getTitle());
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_DESCRIPTION, annonce.getDescription());
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_TYPE, annonce.getType().name());
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_IS_SERVICE_PROVIDER, annonce.isServiceProvider() ? 1 : 0);
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_USER, annonce.getUser());
        return database.insert(MyDatabaseHelper.TABLE_ANNONCES, null, values);
    }

    public Cursor getAnnonce(int id) {
        return database.query(MyDatabaseHelper.TABLE_ANNONCES, null,
                MyDatabaseHelper.COLUMN_ANNONCE_ID + " = ?", new String[] { String.valueOf(id) },
                null, null, null);
    }

    public Cursor getAllAnnonces() {
        return database.query(MyDatabaseHelper.TABLE_ANNONCES, null, null, null, null, null, null);
    }

    public int updateAnnonce(Annonce annonce) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_TITLE, annonce.getTitle());
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_DESCRIPTION, annonce.getDescription());
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_TYPE, annonce.getType().name());
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_IS_SERVICE_PROVIDER, annonce.isServiceProvider() ? 1 : 0);
        values.put(MyDatabaseHelper.COLUMN_ANNONCE_USER, annonce.getUser());
        return database.update(MyDatabaseHelper.TABLE_ANNONCES, values,
                MyDatabaseHelper.COLUMN_ANNONCE_ID + " = ?", new String[] { String.valueOf(annonce.getId()) });
    }

    public void deleteAnnonce(int id) {
        database.delete(MyDatabaseHelper.TABLE_ANNONCES,
                MyDatabaseHelper.COLUMN_ANNONCE_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Annonce getAnnonceById(long annonceId) {
        Annonce annonce = null;
        String[] columns = {
                MyDatabaseHelper.COLUMN_ANNONCE_ID,
                MyDatabaseHelper.COLUMN_ANNONCE_TITLE,
                MyDatabaseHelper.COLUMN_ANNONCE_DESCRIPTION,
                MyDatabaseHelper.COLUMN_ANNONCE_TYPE,
                MyDatabaseHelper.COLUMN_ANNONCE_IS_SERVICE_PROVIDER,
                MyDatabaseHelper.COLUMN_ANNONCE_USER
        };
        String selection = MyDatabaseHelper.COLUMN_ANNONCE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(annonceId) };
        Cursor cursor = database.query(
                MyDatabaseHelper.TABLE_ANNONCES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
      if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String type = cursor.getString(3);
            boolean isServiceProvider = cursor.getInt(4) == 1;  // assuming 1 is true, 0 is false
            String username = cursor.getString(5);
            Annonce.AnnonceType annonceType = Annonce.AnnonceType.valueOf(type);

            annonce = new Annonce(id, title, description, annonceType, isServiceProvider, username);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return annonce;
    }

    public long insertAppointement(Appointement appointement) {
        ContentValues values = new ContentValues();
        values.put("date", formatDate(appointement.getDate()));
        values.put("address", appointement.getAddress());
        values.put("contact", appointement.getContact());

        values.put("provider_name", appointement.getProviderName());
        values.put("customer", appointement.getCustomer());
        values.put("annonce_id", appointement.getAnnonceId());
        values.put("status", appointement.getStatus() ? 1 : 0);

        return database.insert(MyDatabaseHelper.TABLE_APPOI, null, values);
    }

    public List<Appointement> getAllAppointements( String var) {
        try{
        List<Appointement> appointements= new ArrayList<>();
/*        Context context = null;
        // Assuming 'var' is the variable holding the customer name you want to match
           SharedPreferences sharedPreferences =  context.getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
            String customerUsername = sharedPreferences.getString("username", "Anonyme");



   Methode1:         Cursor cursor = database.rawQuery("SELECT * FROM appointement WHERE customer = 'aaa'", null);

            Methode2: String[] columns = {
                    MyDatabaseHelper.COLUMN_APPOINTEMENT_ID,
                    MyDatabaseHelper.COLUMN_DATE,
                    MyDatabaseHelper.COLUMN_ADDRESS,
                    MyDatabaseHelper.COLUMN_CONTACT,
                    MyDatabaseHelper.COLUMN_PROVIDER_NAME,
                    MyDatabaseHelper.COLUMN_CUSTOMER,
                    MyDatabaseHelper.COLUMN_ANNOUNCE_ID,
                    MyDatabaseHelper.COLUMN_STATUS
            };



            String selection = MyDatabaseHelper.COLUMN_CUSTOMER + " = ?";
            String[] selectionArgs = {var};

            Cursor cursor  = database.query(MyDatabaseHelper.TABLE_APPOI,columns, selection, selectionArgs, null, null, null);


*/


        Cursor cursor = database.query("appointement", null, null, null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Appointement appointement = cursorToappointement(cursor);
                appointements.add(appointement);
                cursor.moveToNext();
            }
            cursor.close();
        }
/*

*/
        return appointements; }catch (Exception e){e.printStackTrace();
        return new ArrayList<>();}
    }

    private Appointement cursorToappointement(Cursor cursor) {
        Appointement appointement = new Appointement();
        appointement.setId(cursor.getInt(cursor.getColumnIndex("id")));
        appointement.setDate(parseDate(cursor.getString(cursor.getColumnIndex("date"))));
        appointement.setProviderName(cursor.getString(cursor.getColumnIndex("provider_name")));
        appointement.setCustomer(cursor.getString(cursor.getColumnIndex("customer")));
        appointement.setAnnonceId(cursor.getInt(cursor.getColumnIndex("annonce_id")));
        appointement.setStatus(cursor.getInt(cursor.getColumnIndex("status")) == 1);

        return appointement;
    }
    public Cursor getAppointement(int id) {
        return database.query(MyDatabaseHelper.TABLE_APPOI, null,
                MyDatabaseHelper.COLUMN_APPOINTEMENT_ID + " = ?", new String[] { String.valueOf(id) },
                null, null, null);
    }

    public Appointement getAppointementById(long appointementId) {
        Appointement appointement = null;
        String[] columns = {
                "id",
                "date",
                "address",
                "contact",
                "provider_name",
                "customer",
                "annonce_id",
                "status"
        };
        String selection = "id" + " = ?";
        String[] selectionArgs = { String.valueOf(appointementId) };
        Cursor cursor = database.query(
                "appointement",
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            Date date = parseDate(cursor.getString(1));
            String address = cursor.getString(2);
            String contact = cursor.getString(3);
            String providerName = cursor.getString(4);
            String customer = cursor.getString(5);
            int annonceId = cursor.getInt(6);
            boolean status = cursor.getInt(7) == 1;

            appointement = new Appointement(id, address, contact,date, providerName, customer, annonceId, status);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return appointement;
    }
    public int updateAppointement(Appointement appointement) {
        ContentValues values = new ContentValues();
        values.put("date", formatDate(appointement.getDate()));
        values.put("address", appointement.getAddress());
        values.put("contact", appointement.getContact());
        values.put("provider_name", appointement.getProviderName());
        values.put("customer", appointement.getCustomer());
        values.put("annonce_id", appointement.getAnnonceId());
        values.put("status", appointement.getStatus() ? 1 : 0);

        return database.update(MyDatabaseHelper.TABLE_APPOI, values,
                "id = ?", new String[]{String.valueOf(appointement.getId())});
    }

    public void deleteAppointement(int id) {
        database.delete(MyDatabaseHelper.TABLE_APPOI,
                MyDatabaseHelper.COLUMN_APPOINTEMENT_ID + " = ?", new String[] { String.valueOf(id) });
    }
    public long updateAppointementStatus(int appointementId, boolean newStatus) {
        ContentValues values = new ContentValues();
        values.put("status", newStatus ? 1 : 0); // Assuming 1 is true and 0 is false

        return database.update(MyDatabaseHelper.TABLE_APPOI, values, "id = ?", new String[]{String.valueOf(appointementId)});
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault());
        return dateFormat.format(date);
    }

    public Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault());
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }
}