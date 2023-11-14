// UserListActivity.java
package com.example.wamya;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wamya.adapters.UserListAdapter;
import com.example.wamya.models.User; // Import your User model class
import com.example.wamya.services.MyDatabaseOperations; // Import your database operations class

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Initialize UI components
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewUsers.setLayoutManager(layoutManager);

        // Set up adapter
        userListAdapter = new UserListAdapter(this, new ArrayList<>());
        recyclerViewUsers.setAdapter(userListAdapter);

        // Load and display user data
        loadUsers();
    }

    private void loadUsers() {
        // Use your database operations class to fetch the list of users
        MyDatabaseOperations dbOperations = new MyDatabaseOperations(this);
        dbOperations.open();  // Open the database connection

        // Replace the following with your actual implementation
        List<User> userList = dbOperations.getAllUsers();

        dbOperations.close();  // Close the database connection

        // Update the RecyclerView adapter with the list of users
        userListAdapter.setUsers(userList);
        userListAdapter.notifyDataSetChanged();
    }
}
