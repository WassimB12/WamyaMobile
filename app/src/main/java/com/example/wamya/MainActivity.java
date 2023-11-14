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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.models.User;
import com.example.wamya.services.MyDatabaseHelper;
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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Use MyDatabaseOperations to check login
                MyDatabaseOperations dbOperations = new MyDatabaseOperations(MainActivity.this);
                dbOperations.open();

                // Query the database to check if the user exists and get user data
                Cursor cursor = dbOperations.getUser(username, password);

                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        // Retrieve user data
                        int userId = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ID));
                        String userRoleString = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ROLE));
                        User.UserRole userRole = User.UserRole.valueOf(userRoleString);

                        // Save user data in shared preferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("userId", userId);
                        editor.putString("username", username);
                        editor.putString("userRole", userRole.name());
                        editor.apply();

                        // Login successful, start HomeActivity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Optionally finish the current activity
                    }
                } else {
                    // Login failed, show appropriate message
                    // You can display a message to the user indicating that the login failed
                    showToast("Échec de la connexion. Veuillez vérifier vos informations.");
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

    // Helper method to show toast messages in French
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
