package com.example.wamya.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mydatabase.db";
    public static final int DATABASE_VERSION = 3;

    // Table and column names
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_IS_BLOCKED = "is_blocked";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_ROLE = "role";

    public static final String TABLE_ANNONCES = "annonces";
    public static final String COLUMN_ANNONCE_ID = "id";
    public static final String COLUMN_ANNONCE_TITLE = "title";
    public static final String COLUMN_ANNONCE_DESCRIPTION = "description";
    public static final String COLUMN_ANNONCE_TYPE = "type";
    public static final String COLUMN_ANNONCE_IS_SERVICE_PROVIDER = "is_service_provider";
    public static final String COLUMN_ANNONCE_USER = "user";
    //appointement
    public static final String TABLE_APPOI = "appointement";
    public static final String COLUMN_APPOINTEMENT_ID = "id";

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CONTACT = "contact";

    public static final String COLUMN_PROVIDER_NAME = "provider_name";
    public static final String COLUMN_CUSTOMER = "customer";
    public static final String COLUMN_ANNOUNCE_ID = "annonce_id";
    public static final String COLUMN_STATUS = "status";

    // Constructor
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table
        String createUserTable = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_IS_BLOCKED + " INTEGER," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_PHONE_NUMBER + " TEXT," +
                COLUMN_ROLE + " TEXT" +
                ");";
        db.execSQL(createUserTable);

        String createAnnoncesTable = "CREATE TABLE " + TABLE_ANNONCES + "("
                + COLUMN_ANNONCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ANNONCE_TITLE + " TEXT,"
                + COLUMN_ANNONCE_DESCRIPTION + " TEXT,"
                + COLUMN_ANNONCE_TYPE + " TEXT,"
                + COLUMN_ANNONCE_IS_SERVICE_PROVIDER + " INTEGER,"
                + COLUMN_ANNONCE_USER + " TEXT"
                + ");";

        // Execute the SQL statements to create new tables
        db.execSQL(createAnnoncesTable);
        // appoi table
        String createAppoiTable =
                "CREATE TABLE " + TABLE_APPOI + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_DATE + " TEXT," +COLUMN_ADDRESS + " TEXT,"+COLUMN_CONTACT + " TEXT,"+
                        COLUMN_PROVIDER_NAME + " TEXT," +
                        COLUMN_CUSTOMER + " TEXT," +
                        COLUMN_ANNOUNCE_ID + " INTEGER," +
                        COLUMN_STATUS + " INTEGER DEFAULT 0);";
        db.execSQL(createAppoiTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrades here
        // Example: db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS + ";");
        // onCreate(db);
    }
}

