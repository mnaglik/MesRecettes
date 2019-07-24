package com.example.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Creation_recette extends AppCompatActivity {
    private TextView txtTitre;
    private DataBaseManager_Recette dbmR;
    private TextView btnSuivant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_recette_titre);

        dbmR = new DataBaseManager_Recette(this);
        creationTitre();
        ///insertionPhoto();
    }

    public void creationTitre(){
        txtTitre = findViewById(R.id.txttitre);
        btnSuivant = findViewById(R.id.btnSuivant);
        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_creation_recette_photo);
              dbmR.insert(txtTitre.getText().toString());

             // Intent i = new Intent(getApplicationContext(),Creation_recette.class);

            //  startActivity(i);

            }
        });


    }


}
