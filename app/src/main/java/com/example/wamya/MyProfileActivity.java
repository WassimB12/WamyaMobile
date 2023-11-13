package com.example.wamya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

        // Set up UI with user data (you need to load the user data from the database)
        loadUserData();

        // Set click listener for the save changes button
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save changes logic
                saveChanges();
            }
        });
    }

    private void loadUserData() {
        // TODO: Load user data from the database and populate UI components
        // For example:
        // User user = loadUserFromDatabase();
        // usernameTextView.setText("Nom d'utilisateur : " + user.getUsername());
        // passwordEditText.setText(user.getPassword());
        // emailEditText.setText(user.getEmail());
        // addressEditText.setText(user.getAddress());
        // phoneNumberEditText.setText(user.getPhoneNumber());
    }

    private void saveChanges() {
        // TODO: Save the changes made by the user to the database
        // For example:
        // String newPassword = passwordEditText.getText().toString();
        // String newEmail = emailEditText.getText().toString();
        // String newAddress = addressEditText.getText().toString();
        // String newPhoneNumber = phoneNumberEditText.getText().toString();
        // updateUserDataInDatabase(newPassword, newEmail, newAddress, newPhoneNumber);
    }
}