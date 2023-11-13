package com.example.wamya;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;

public class CreateAppointement extends AppCompatActivity {

    private EditText editTextDate, editTextAddress, editTextContact;
    private Button buttonSaveAppointment;

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
                        editTextDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
}
