package com.example.wamya.models;

import java.util.Date;

public class Notification {
    public enum NotificationType {
        Reservation,
        Evaluation,

    }
    private NotificationType type;
    private boolean isSeen;
    private Date creationDate;
    private User user; // Reference to the user associated with the notification

    // Constructors, getters, and setters
}
