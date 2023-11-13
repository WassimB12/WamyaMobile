package com.example.wamya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wamya.models.Annonce;
import com.example.wamya.services.MyDatabaseOperations;

public class ModifierAnnonceActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private Spinner spinnerType;
    private Switch switchServiceProvider;

    private MyDatabaseOperations dbOperations;
    private Annonce existingAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_annonce);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerType = findViewById(R.id.spinnerType);
        switchServiceProvider = findViewById(R.id.switchServiceProvider);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.annonce_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        dbOperations = new MyDatabaseOperations(this);
        dbOperations.open();

        long annonceId = getIntent().getIntExtra("ANNONCE_ID", -1);
        System.out.println("Annonce id: " + annonceId);
        if (annonceId != -1) {
            existingAnnonce = dbOperations.getAnnonceById(annonceId);
            if (existingAnnonce != null) {
                System.out.println("Existing annonce: " + existingAnnonce.getTitle());
                populateUIWithAnnonceDetails(existingAnnonce);
            }
        }

        findViewById(R.id.buttonSaveChanges).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAnnonceChanges();
            }
        });
    }

    private void populateUIWithAnnonceDetails(Annonce annonce) {
        editTextTitle.setText(annonce.getTitle());
        editTextDescription.setText(annonce.getDescription());
        spinnerType.setSelection(((ArrayAdapter) spinnerType.getAdapter()).getPosition(annonce.getType().name()));
        switchServiceProvider.setChecked(annonce.isServiceProvider());
    }

    private void saveAnnonceChanges() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String type = spinnerType.getSelectedItem().toString();
        boolean isServiceProvider = switchServiceProvider.isChecked();

        Annonce.AnnonceType annonceType;
        try {
            annonceType = Annonce.AnnonceType.valueOf(type);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Invalid type selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the existing Annonce object with the new details
        existingAnnonce.setTitle(title);
        existingAnnonce.setDescription(description);
        existingAnnonce.setType(annonceType);
        existingAnnonce.setServiceProvider(isServiceProvider);

        // Update the Annonce in the database
        long result = dbOperations.updateAnnonce(existingAnnonce);
        if (result > 0) {
            Toast.makeText(ModifierAnnonceActivity.this, "Annonce updated!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(ModifierAnnonceActivity.this, "Error updating annonce.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbOperations.close();
        super.onDestroy();
    }
}
