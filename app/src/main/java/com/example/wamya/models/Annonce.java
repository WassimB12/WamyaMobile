package com.example.wamya.models;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

public class Annonce {

    private int id;

    public enum AnnonceType {
        Plomberie,
        Electricité,
        Autre
    }
    private String title;
    private String description;
    private AnnonceType type;
    private boolean isServiceProvider;
    private String user;
    private List<Reservation> reservations;
    private List<Evaluation> evaluations;

    public Annonce(int id, String title, String description, AnnonceType type, boolean isServiceProvider, String user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.isServiceProvider = isServiceProvider;
        this.user = user;
    }

    public Annonce(String title, String description, AnnonceType annonceType, boolean isServiceProvider, String user) {
        this.title = title;
        this.description = description;
        this.type = annonceType;
        this.isServiceProvider = isServiceProvider;
        this.user = user;
    }

    public Annonce(String title, String description, AnnonceType annonceType, boolean isServiceProvider) {
        this.title = title;
        this.description = description;
        this.type = annonceType;
        this.isServiceProvider = isServiceProvider;
        this.reservations = null;
        this.evaluations = null;
        this.user = null;
    }

    @SuppressLint("Range")
    public Annonce(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.title = cursor.getString(cursor.getColumnIndex("title"));
        this.description = cursor.getString(cursor.getColumnIndex("description"));
        this.type = AnnonceType.valueOf(cursor.getString(cursor.getColumnIndex("type")));
        this.user = cursor.getString(cursor.getColumnIndex("user"));
        this.isServiceProvider = cursor.getInt(cursor.getColumnIndex("is_service_provider")) == 1;
       }
    public Annonce(String title, String description, AnnonceType type, boolean isServiceProvider, String user, List<Reservation> reservations, List<Evaluation> evaluations) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.isServiceProvider = isServiceProvider;
        this.user = user;
        this.reservations = reservations;
        this.evaluations = evaluations;
    }

    public Annonce(int id, String title, String description, AnnonceType type, boolean isServiceProvider, String user, List<Reservation> reservations, List<Evaluation> evaluations) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.isServiceProvider = isServiceProvider;
        this.user = user;
        this.reservations = reservations;
        this.evaluations = evaluations;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AnnonceType getType() {
        return type;
    }

    public void setType(AnnonceType type) {
        this.type = type;
    }

    public boolean isServiceProvider() {
        return isServiceProvider;
    }

    public void setServiceProvider(boolean serviceProvider) {
        isServiceProvider = serviceProvider;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}