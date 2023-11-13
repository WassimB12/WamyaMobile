package com.example.wamya;

import android.content.Context;
import android.content.Intent;
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

        // Set up item click listener
        popupMenu.setOnMenuItemClickListener(item -> {
            // Handle item clicks
            if (item.getItemId() == R.id.action_mon_profile) {
                Intent intent = new Intent(context, MyProfileActivity.class);
                context.startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.action_mes_annonces) {
                showToast("Mes Annonces clicked");
                return true;
            } else if (item.getItemId() == R.id.action_mes_reservations) {
                showToast("Mes Reservations clicked");
                return true;
            } else if (item.getItemId() == R.id.action_deconnecter) {
                showToast("Deconnecter clicked");
                return true;
            } else {
                return false;
            }
        });

        // Show the popup menu
        popupMenu.show();
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
