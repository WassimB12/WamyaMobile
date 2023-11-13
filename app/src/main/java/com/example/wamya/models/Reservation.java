package com.example.wamya.models;

import java.util.Date;
import java.util.List;

public class Reservation {
    public enum ReservationState {
        ACCEPTEE,
        REFUSEE
    }
    private List<Date> listDate;
    private double price;
    private String description;
    private ReservationState state;
    private User user; // Reference to the user who made the reservation
    private Annonce annonce; // Reference to the annonce associated with the reservation

    // Constructors, getters, and setters
}
