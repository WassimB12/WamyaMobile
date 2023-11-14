package com.example.wamya;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.adapters.AnnonceAdapter;
import com.example.wamya.adapters.AppointementAdapter;
import com.example.wamya.models.Annonce;
import com.example.wamya.models.Appointement;
import com.example.wamya.services.MyDatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class AppointementList extends AppCompatActivity { private ListView appointmentListView;
        private Button addAppointmentButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.appointement_list);

            appointmentListView = findViewById(R.id.appointmentList);
            addAppointmentButton = findViewById(R.id.addAppointmentButton);

            // Sample data for the appointment list
            List<Appointement> appointements = fetchAppointements();
            AppointementAdapter adapter = new AppointementAdapter(this,appointements);
            appointmentListView.setAdapter(adapter);

            // Handle item click on the list view
            appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    Toast.makeText(AppointementList.this, "Selected Appointment: " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            });

            addAppointmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(AppointementList.this, HomeActivity.class);

                   startActivity(i);
                }
            });

        }
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
   public void refresh(){ appointmentListView = findViewById(R.id.appointmentList);

       List<Appointement> appointements = fetchAppointements();
       AppointementAdapter adapter = new AppointementAdapter(this,appointements);
       appointmentListView.setAdapter(adapter);}

    private List<Appointement> fetchAppointements() {
        MyDatabaseOperations dbOperations = new MyDatabaseOperations(this);
        dbOperations.open();
        List<Appointement> appointements =  dbOperations.getAllAppointements();
        dbOperations.close();
        // Assuming this method returns a Cursor
       return appointements;
    }


}

