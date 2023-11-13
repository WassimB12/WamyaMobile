package com.example.wamya;

// RegisterActivity.java
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.services.MyDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, emailEditText, addressEditText, phoneNumberEditText;
    private Button registerButton;
    private TextView signInTextView;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        registerButton = findViewById(R.id.registerButton);
        signInTextView = findViewById(R.id.signInTextView);


        dbHelper = new MyDatabaseHelper(this);


        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate and handle registration logic
                if (validateInputs()) {
                    // Registration logic here
                    registerUser();
                }
            }
        });
        // Set click listener for the sign-in text view
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the login page (MainActivity in this case)
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optionally finish the current activity
            }
        });

        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate and handle registration logic
                if (validateInputs()) {
                    // Registration logic here
                    registerUser();
                }
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate each input field and show error messages
        if (TextUtils.isEmpty(usernameEditText.getText().toString())) {
            usernameEditText.setError("Ce champ est obligatoire");
            isValid = false;
        }

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError("Ce champ est obligatoire");
            isValid = false;
        }

        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            emailEditText.setError("Ce champ est obligatoire");
            isValid = false;
        }

        if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            addressEditText.setError("Ce champ est obligatoire");
            isValid = false;
        }

        if (TextUtils.isEmpty(phoneNumberEditText.getText().toString())) {
            phoneNumberEditText.setError("Ce champ est obligatoire");
            isValid = false;
        }

        return isValid;
    }
    private void registerUser() {
        // Get the values from UI components
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        // Get the writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to insert values into the database
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_USERNAME, username);
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, password);
        values.put(MyDatabaseHelper.COLUMN_EMAIL, email);
        values.put(MyDatabaseHelper.COLUMN_ADDRESS, address);
        values.put(MyDatabaseHelper.COLUMN_PHONE_NUMBER, phoneNumber);

        // Insert a new user into the database
        long newRowId = db.insert(MyDatabaseHelper.TABLE_USERS, null, values);

        // Check if the insertion was successful
        if (newRowId != -1) {
            // Registration successful
            // You may want to show a success message or navigate to another activity
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            // Registration failed
            // You may want to show an error message
        }

        // Close the database connection
        dbHelper.close();
    }
}