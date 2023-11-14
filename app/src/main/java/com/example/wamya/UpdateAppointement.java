package com.example.wamya;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.models.Annonce;
import com.example.wamya.models.Appointement;
import com.example.wamya.services.MyDatabaseOperations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateAppointement extends AppCompatActivity {
    private EditText editTextDate, editTextAddress, editTextContact;
    private TextView title;
    private Button buttonSaveAppointment;
    private MyDatabaseOperations myDb;
    Appointement existingAppointement;
    int annonceId;
    String serviceProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        editTextDate = findViewById(R.id.editTextDate);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextContact = findViewById(R.id.editTextContact);
        buttonSaveAppointment = findViewById(R.id.buttonSaveAppointment);
title=findViewById(R.id.appointmentTitle);
title.setText("Modifier votre rendez-vous");
        editTextDate.setFocusable(false);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



        buttonSaveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAppointement();
                // Implement your logic to save the appointment here
                Intent intent = new Intent(UpdateAppointement.this, AppointementList.class);
                startActivity(intent);

            }
        });
        myDb= new MyDatabaseOperations(this);
        myDb.open();

        long appointementId = getIntent().getIntExtra("APPOINTEMENT_ID", -1);
        System.out.println("Appoi id: " + appointementId);
        if (appointementId != -1) {
            existingAppointement = myDb.getAppointementById(appointementId);
            if (existingAppointement != null) {
                System.out.println("Existing Appointement: " + existingAppointement.getStatus());
            }
        }
        // Retrieve additional data using more getIntExtra, getStringExtra, etc.
    }

  
    public void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }


    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    public Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }


    private void editAppointement() {
        Date date = parseDate(editTextDate.getText().toString());
        if (date == null) {
            Calendar calendar = Calendar.getInstance();
            date =parseDate(formatDate(calendar.getTime()));
        }
        String adress = editTextAddress.getText().toString();
        String contact = editTextContact.getText().toString();

        // Update the existing Annonce object with the new details
        existingAppointement.setDate(date);
        existingAppointement.setAddress(adress);
        existingAppointement.setContact(contact);

        // Update the Annonce in the database
        long result = myDb.updateAppointement(existingAppointement);
        if (result > 0) {
            Toast.makeText(UpdateAppointement.this, "Rendez vous modifié!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(UpdateAppointement.this, "Erreur.", Toast.LENGTH_SHORT).show();
        }
    }


}

