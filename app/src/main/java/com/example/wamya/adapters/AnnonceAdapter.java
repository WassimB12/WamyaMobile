package com.example.wamya.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wamya.CreateAppointement;
import com.example.wamya.HomeActivity;
import com.example.wamya.ModifierAnnonceActivity;
import com.example.wamya.R;
import com.example.wamya.models.Annonce;
import com.example.wamya.services.MyDatabaseHelper;
import com.example.wamya.services.MyDatabaseOperations;

import java.util.List;
import android.widget.ImageButton;
import android.widget.Toast;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder> {

    private List<Annonce> annoncesList;

    // Constructor
    public AnnonceAdapter(List<Annonce> annoncesList) {
        this.annoncesList = annoncesList;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annonce, parent, false);
        return new AnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position) {
        Annonce annonce = annoncesList.get(position);

        holder.titleTextView.setText(annonce.getTitle());
        holder.typeTextView.setText(annonce.getType().toString());
        holder.descriptionTextView.setText(annonce.getDescription());
        if (!annonce.isServiceProvider()){
            holder.layout.setBackgroundColor(0xFFFF8A65);
            holder.typeServiceTextView.setText("Demande de service");
        }
        try{
            holder.userTextView.setText("Publié par "+annonce.getUser());
        } catch (Exception e) {
            holder.userTextView.setText("Publié en tant que anonyme");
        }
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(v.getContext(), ModifierAnnonceActivity.class);
            editIntent.putExtra("ANNONCE_ID", annonce.getId());
                v.getContext().startActivity(editIntent);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseOperations db = new MyDatabaseOperations(v.getContext());
                db.open();
                db.deleteAnnonce(annonce.getId());
                db.close();
                Toast.makeText(v.getContext(), "Annonce supprimée", Toast.LENGTH_SHORT).show();
                ((HomeActivity)v.getContext()).load();
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdvIntent = new Intent(v.getContext(), CreateAppointement.class);
                rdvIntent.putExtra("ANNONCE_ID", annonce.getId());
                v.getContext().startActivity(rdvIntent);
            }
        });







    }

    @Override
    public int getItemCount() {
        return annoncesList.size();
    }

    public static class AnnonceViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView typeTextView;
        TextView typeServiceTextView;
        TextView descriptionTextView;
        TextView userTextView;
        ImageButton editButton;

        ImageButton deleteButton;
      //  Button prendreRDV;

        LinearLayout layout;

        public AnnonceViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            typeServiceTextView = itemView.findViewById(R.id.typeService);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            userTextView = itemView.findViewById(R.id.user);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            layout = itemView.findViewById(R.id.layout);
         //   prendreRDV =itemView.findViewById(R.id.button);
        }
    }
}




