package com.example.wamya;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.wamya.models.Annonce;
import com.example.wamya.models.Appointement;
import com.example.wamya.services.MyDatabaseOperations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateAppointement extends AppCompatActivity {

    private EditText editTextDate, editTextAddress, editTextContact;
    private Button buttonSaveAppointment;
    private MyDatabaseOperations myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        editTextDate = findViewById(R.id.editTextDate);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextContact = findViewById(R.id.editTextContact);
        buttonSaveAppointment = findViewById(R.id.buttonSaveAppointment);

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
                createAppointement();
                // Implement your logic to save the appointment here
                Intent intent = new Intent(CreateAppointement.this, AppointementList.class);
                startActivity(intent);

            }
        });
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
    private void createAppointement() {
        String contact = editTextContact.getText().toString();
        String address = editTextAddress.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        String customerUsername = sharedPreferences.getString("username", "Anonyme");


        Date date = parseDate(editTextDate.getText().toString());
        if (date == null) {
            Calendar calendar = Calendar.getInstance();
            date =calendar.getTime();
            }
        int idAppoi = 1;

        MyDatabaseOperations dbOperations = new MyDatabaseOperations(this);
        dbOperations.open();
        Appointement appointement = new Appointement
                (date,address,contact, null, customerUsername, 3, false);
        long result = dbOperations.insertAppointement(appointement);
        dbOperations.close();

        if (result != -1) {
            Toast.makeText(CreateAppointement.this, "Rendez vous proposé!", Toast.LENGTH_SHORT).show();
            idAppoi = +1;
            finish();
        } else {
            Toast.makeText(CreateAppointement.this, "Erreur.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    }

