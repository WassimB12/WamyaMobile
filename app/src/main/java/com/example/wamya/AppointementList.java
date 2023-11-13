package com.example.wamya;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointementList extends AppCompatActivity { private ListView appointmentListView;
        private Button addAppointmentButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.appointement_list);

            appointmentListView = findViewById(R.id.appointmentList);
            addAppointmentButton = findViewById(R.id.addAppointmentButton);

            // Sample data for the appointment list
            String[] appointmentData = {"Appointment 1", "Appointment 2", "Appointment 3"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_appointment, R.id.appointmentTitle, appointmentData);
            appointmentListView.setAdapter(adapter);

            // Handle item click on the list view
            appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    Toast.makeText(AppointementList.this, "Selected Appointment: " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            });}}

