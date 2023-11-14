package com.example.wamya.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

import com.example.wamya.AppointementList;
import com.example.wamya.CreateAppointement;
import com.example.wamya.HomeActivity;
import com.example.wamya.ModifierAnnonceActivity;
import com.example.wamya.R;
import com.example.wamya.UpdateAppointement;
import com.example.wamya.models.Appointement;
import com.example.wamya.services.MyDatabaseOperations;

import java.util.List;

public class AppointementAdapter extends ArrayAdapter<Appointement> {

    private List<Appointement> appointementList;
    private Context context;



    public AppointementAdapter(Context context, List<Appointement> appointementList) {
        super(context, 0, appointementList);
        this.context = context;
        this.appointementList = appointementList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {










        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_appointment, parent, false);
        }

        Appointement currentAppointement = getItem(position);

        if (currentAppointement != null) {
           /* TextView titleTextView = listItemView.findViewById(R.id.titleTextView);
            titleTextView.setText("Annonce "+currentAppointement.getAnnonceId());
*/
            TextView dateTextView = listItemView.findViewById(R.id.appointmentDate);
            dateTextView.setText("Date: " + currentAppointement.getDate());

            TextView timeTextView = listItemView.findViewById(R.id.appointmentTime);
            timeTextView.setText("Adress: " + currentAppointement.getAddress());

            TextView serviceProviderTextView = listItemView.findViewById(R.id.appointmentServiceProv);
            serviceProviderTextView.setText("Service provider: " +currentAppointement.getProviderName());

            ToggleButton acceptedToggleButton = listItemView.findViewById(R.id.acceptedToggleButton);
            acceptedToggleButton.setChecked(currentAppointement.getStatus());


            ImageButton editButton=listItemView.findViewById(R.id.imageButton2);
            ImageButton deleteButton=listItemView.findViewById(R.id.imageButton3);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editIntent = new Intent(v.getContext(), UpdateAppointement.class);
                    editIntent.putExtra("APPOINTEMENT_ID", currentAppointement.getId());
                    v.getContext().startActivity(editIntent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDatabaseOperations db = new MyDatabaseOperations(v.getContext());
                    db.open();
                    db.deleteAppointement(currentAppointement.getId());
                    db.close();
                    Toast.makeText(v.getContext(), "Rendez vous supprimée", Toast.LENGTH_SHORT).show();
                    ((AppointementList)v.getContext()).refresh();
                }
            });


        }

        return listItemView;
    }
}
