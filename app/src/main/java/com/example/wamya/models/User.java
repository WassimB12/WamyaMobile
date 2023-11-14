package com.example.wamya.models;

import java.util.List;

public class User {

    private int id;
    public enum UserRole {
        ADMIN,
        USER
    }

    private String username;
    private String password;
    private String email;
    private boolean isBlocked;
    private String address;
    private String phoneNumber;
    private UserRole role;
    private List<Annonce> annonces;
    private List<Reservation> reservations;
    private List<Notification> notifications;
    private List<Evaluation> evaluations;

    // Constructors, getters, and setters

    public int getId() {
        return id;
    }

    public User(int id) {
        this.id = id;
    }
    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {
    }

    public User(int id, String username, String password, String email, boolean isBlocked, String address, String phoneNumber, UserRole role, List<Annonce> annonces, List<Reservation> reservations, List<Notification> notifications, List<Evaluation> evaluations) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isBlocked = isBlocked;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.annonces = annonces;
        this.reservations = reservations;
        this.notifications = notifications;
        this.evaluations = evaluations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}