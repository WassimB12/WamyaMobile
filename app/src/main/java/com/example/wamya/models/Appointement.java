package com.example.wamya.models;

import android.database.Cursor;

import java.util.Date;

public class Appointement {

    private int id;
    private String address;
private String contact ;
    private Date date;
    private String providerName;
    private String customer;
    private int annonceId;
    private Boolean status=false;


    public Appointement(Date date, String address, String contact,  String providerName, String customer, int annonceId, Boolean status) {
        this.address = address;
        this.contact = contact;
        this.date = date;
        this.providerName = providerName;
        this.customer = customer;
        this.annonceId = annonceId;
        this.status = status;
    }

    public Appointement(int id, String address, String contact, Date date, String providerName, String customer, int annonceId, Boolean status) {
        this.id = id;
        this.address = address;
        this.contact = contact;
        this.date = date;
        this.providerName = providerName;
        this.customer = customer;
        this.annonceId = annonceId;
        this.status = status;
    }

    public Appointement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getAnnonceId() {
        return annonceId;
    }

    public void setAnnonceId(int annonceId) {
        this.annonceId = annonceId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

