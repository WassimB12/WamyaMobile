package com.example.wamya;

// MainActivity.java
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.services.MyDatabaseOperations;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView forgetPasswordTextView, signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgetPasswordTextView = findViewById(R.id.forgetPasswordTextView);
        signUpTextView = findViewById(R.id.signUpTextView);

        // Set click listener for the login button
// Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Use MyDatabaseOperations to check login
                MyDatabaseOperations dbOperations = new MyDatabaseOperations(MainActivity.this);
                dbOperations.open();

                // Query the database to check if the user exists and the password is correct
                Cursor cursor = dbOperations.getUser(username, password);

                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()){
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.apply();
                    }
                    // Login successful
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Optionally finish the current activity
                } else {
                    // Login failed, show appropriate message
                    // You can display a message to the user indicating that the login failed
                }

                // Close the cursor and database
                if (cursor != null) {
                    cursor.close();
                }
                dbOperations.close();
            }
        });

        // Set click listener for the forget password text view
        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to forget password page
            }
        });

        // Set click listener for the sign-up text view
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to register page
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
