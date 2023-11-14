package com.example.wamya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class TopBarHandler {

    private Context context;

    public TopBarHandler(Context context) {
        this.context = context;
    }

    public void setupNotificationButton(ImageButton notificationButton) {
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle notification button click (show dropdown menu, etc.)
                showNotificationDropDownMenu(v);
            }
        });
    }

    public void setupUserButton(ImageButton userButton) {
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle user button click (show dropdown menu, etc.)
                showUserDropDownMenu(v);
            }
        });
    }

    private void showNotificationDropDownMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.notification_menu, popupMenu.getMenu());

        // Set up item click listener
        popupMenu.setOnMenuItemClickListener(item -> {
            // Handle item clicks
            if (item.getItemId() == R.id.action_notification1) {
                showToast("Notification 1 clicked");
                return true;
            } else if (item.getItemId() == R.id.action_notification2) {
                showToast("Notification 2 clicked");
                return true;
            } else if (item.getItemId() == R.id.action_all_notifications) {
                showToast("Tous mes notifications clicked");
                return true;
            } else {
                return false;
            }
        });

        // Show the popup menu
        popupMenu.show();
    }

    private void showUserDropDownMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.user_menu, popupMenu.getMenu());

        // Check if the user is an admin
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("userRole", "");

        Menu menu = popupMenu.getMenu();

        // Set up item click listener
        popupMenu.setOnMenuItemClickListener(item -> {
            // Handle item clicks
            if (item.getItemId() == R.id.action_mon_profile) {
                Intent intent = new Intent(context, MyProfileActivity.class);
                context.startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.action_liste_des_utilisateurs) {
                if ("ADMIN".equals(userRole)) {
                    // Only allow admins to access the user list
                    Intent intent = new Intent(context, UserListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    showToast("You don't have permission to access this feature.");
                }
                return true;
            } else if (item.getItemId() == R.id.action_mes_annonces) {
                showToast("Mes Annonces clicked");
                return true;
            } else if (item.getItemId() == R.id.action_mes_reservations) {

                Intent intent = new Intent(context, AppointementList.class); // Replace with your login activity class
                context.startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.action_deconnecter) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("userId");
                editor.remove("userRole");
                editor.apply();

                // Navigate back to the login screen
                Intent intent = new Intent(context, MainActivity.class); // Replace with your login activity class
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            } else {
                return false;
            }
        });

        // Check the user role
        Log.d("UserRole", "User Role: " + userRole);

// Show/hide the "liste des utilisateurs" menu item based on user role
        MenuItem listeDesUtilisateursItem = menu.findItem(R.id.action_liste_des_utilisateurs);
        if ("ADMIN".equals(userRole)) {
            listeDesUtilisateursItem.setVisible(true);
            Log.d("Visibility", "Setting visibility to true");
        } else {
            listeDesUtilisateursItem.setVisible(false);
            Log.d("Visibility", "Setting visibility to false");
        }


        // Show the popup menu
        popupMenu.show();
    }



    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
