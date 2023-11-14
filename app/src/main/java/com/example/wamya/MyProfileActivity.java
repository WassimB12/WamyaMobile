package com.example.wamya;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.models.User;
import com.example.wamya.services.MyDatabaseOperations;

public class MyProfileActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private EditText passwordEditText, emailEditText, addressEditText, phoneNumberEditText;
    private Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Set up the toolbar
        ImageButton notificationButton = findViewById(R.id.notificationButton);
        ImageButton userButton = findViewById(R.id.userButton);
        TopBarHandler topBarHandler = new TopBarHandler(this);
        topBarHandler.setupNotificationButton(notificationButton);
        topBarHandler.setupUserButton(userButton);

        // Initialize UI components
        usernameTextView = findViewById(R.id.usernameTextView);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        // Load user data when the activity is created
        new LoadUserDataTask().execute();

        // Set click listener for the save changes button
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save changes logic
                saveChanges();
            }
        });
    }

    private void updateUI(User user) {
        if (user != null) {
            usernameTextView.setText("Nom d'utilisateur : " + user.getUsername());
            passwordEditText.setText(user.getPassword());
            emailEditText.setText(user.getEmail());
            addressEditText.setText(user.getAddress());
            phoneNumberEditText.setText(user.getPhoneNumber());
        } else {
            // Handle the case where user data couldn't be loaded
            Toast.makeText(MyProfileActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveChanges() {
        String newPassword = passwordEditText.getText().toString();
        String newEmail = emailEditText.getText().toString();
        String newAddress = addressEditText.getText().toString();
        String newPhoneNumber = phoneNumberEditText.getText().toString();

        // Check if any of the fields is empty
        if (newPassword.isEmpty() || newEmail.isEmpty() || newAddress.isEmpty() || newPhoneNumber.isEmpty()) {
            Toast.makeText(MyProfileActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update user data in the database
        new UpdateUserDataTask(newPassword, newEmail, newAddress, newPhoneNumber).execute();
    }

    private class UpdateUserDataTask extends AsyncTask<Void, Void, Boolean> {
        private String newPassword, newEmail, newAddress, newPhoneNumber;

        public UpdateUserDataTask(String newPassword, String newEmail, String newAddress, String newPhoneNumber) {
            this.newPassword = newPassword;
            this.newEmail = newEmail;
            this.newAddress = newAddress;
            this.newPhoneNumber = newPhoneNumber;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            // Update user data in the database
            MyDatabaseOperations dbOperations = new MyDatabaseOperations(MyProfileActivity.this);
            dbOperations.open();  // Open the database connection

            // Replace the following with your actual implementation
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1);  // Replace "USER_ID_KEY" with your actual key

            // Retrieve the user from the database
            User user = dbOperations.getUserById(userId);

            if (user != null) {
                // Update user attributes
                user.setPassword(newPassword);
                user.setEmail(newEmail);
                user.setAddress(newAddress);
                user.setPhoneNumber(newPhoneNumber);

                // Perform the update in the database
                int rowsAffected = dbOperations.updateUserById(userId, user);

                dbOperations.close();  // Close the database connection

                return rowsAffected > 0;
            } else {
                dbOperations.close();  // Close the database connection
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean updateSuccessful) {
            if (updateSuccessful) {
                Toast.makeText(MyProfileActivity.this, "User data updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyProfileActivity.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class LoadUserDataTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... voids) {
            // Load user data from the database
            MyDatabaseOperations dbOperations = new MyDatabaseOperations(MyProfileActivity.this);
            dbOperations.open();  // Open the database connection

            // Replace the following with your actual implementation
            // For example, assuming you have a method to get the user by ID:
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1);  // Replace "USER_ID_KEY" with your actual key
            User user = dbOperations.getUserById(userId);

            dbOperations.close();  // Close the database connection

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            // Update UI with loaded user data
            updateUI(user);
        }
    }
}
