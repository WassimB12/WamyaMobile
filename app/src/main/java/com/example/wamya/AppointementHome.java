package com.example.wamya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AppointementHome extends AppCompatActivity {

    private EditText searchBar, publicationTextView;
    private Button postButton;
    private ImageView icon1, icon2; // Reference to your category icons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement_home);

        searchBar = findViewById(R.id.searchBar);
        publicationTextView = findViewById(R.id.publicationTextView);
        postButton = findViewById(R.id.postButton);
//        icon1 = findViewById(R.id.icon1);
//        icon2 = findViewById(R.id.icon2);

     postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your logic to save the appointment here
                Intent intent1 = new Intent(AppointementHome.this, CreateAppointement.class);
                startActivity(intent1);

            }
        });
        // Implement search functionality
        // Implement posting a publication functionality
        // You can add onClickListeners for the icons if they have specific actions
    }
}
