package com.example.wamya;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wamya.adapters.AnnonceAdapter;
import com.example.wamya.models.Annonce;
import com.example.wamya.services.MyDatabaseOperations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RecyclerView recyclerViewAnnonces = findViewById(R.id.recyclerViewAnnonces);
        recyclerViewAnnonces.setLayoutManager(new LinearLayoutManager(this));

        // Assuming you have a method to fetch annonces and it returns a List<Annonce>
        List<Annonce> annonceList = fetchAnnonces();

        // Set the adapter
        AnnonceAdapter adapter = new AnnonceAdapter(annonceList);
        recyclerViewAnnonces.setAdapter(adapter);
        FloatingActionButton fabCreateAnnonce = findViewById(R.id.fabCreateAnnonce);
        fabCreateAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the CreerAnnonceActivity
                Intent intent = new Intent(HomeActivity.this, CreerAnnonceActivity.class);
                startActivity(intent);
            }
        });

        // Set up the toolbar
        ImageButton notificationButton = findViewById(R.id.notificationButton);
        ImageButton userButton = findViewById(R.id.userButton);
        TopBarHandler topBarHandler = new TopBarHandler(this);
        topBarHandler.setupNotificationButton(notificationButton);
        topBarHandler.setupUserButton(userButton);


    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    public void load() {
        RecyclerView recyclerViewAnnonces = findViewById(R.id.recyclerViewAnnonces);
        recyclerViewAnnonces.setLayoutManager(new LinearLayoutManager(this));
        List<Annonce> annonceList = fetchAnnonces();
        AnnonceAdapter adapter = new AnnonceAdapter(annonceList);
        recyclerViewAnnonces.setAdapter(adapter);
    }

    private List<Annonce> fetchAnnonces() {
        MyDatabaseOperations dbOperations = new MyDatabaseOperations(this);
        dbOperations.open();
        Cursor cursor = dbOperations.getAllAnnonces(); // Assuming this method returns a Cursor
        List<Annonce> annonces = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
               Annonce annonce = new Annonce(cursor);
                annonces.add(annonce);
            } while (cursor.moveToNext());
            cursor.close();
        }

        dbOperations.close();
        return annonces;
    }






    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
