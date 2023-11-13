package com.example.wamya;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.models.Annonce;
import com.example.wamya.models.User;
import com.example.wamya.services.MyDatabaseOperations;

public class CreerAnnonceActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private Spinner spinnerType;
    private Switch switchServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_annonce);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerType = findViewById(R.id.spinnerType);
        switchServiceProvider = findViewById(R.id.switchServiceProvider);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Plomberie", "Electricité", "Autre"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        findViewById(R.id.buttonCreateAnnonce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAnnonce();
            }
        });
    }

    private void createAnnonce() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String type = spinnerType.getSelectedItem().toString();
        boolean isServiceProvider = switchServiceProvider.isChecked();

        Annonce.AnnonceType annonceType = Annonce.AnnonceType.valueOf(type);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Anonyme");


        MyDatabaseOperations dbOperations = new MyDatabaseOperations(this);
        dbOperations.open();
        Annonce newAnnonce = new Annonce(title, description, annonceType, isServiceProvider, username);
        long result = dbOperations.insertAnnonce(newAnnonce);
        dbOperations.close();

        if (result != -1) {
            Toast.makeText(CreerAnnonceActivity.this, "Annonce crée!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(CreerAnnonceActivity.this, "Erreur.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
