package com.example.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MesRecettes extends AppCompatActivity {

    private TextView txtAjout, txtVisu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_recettes);

        createButton();

    }

    public void createButton(){

        txtAjout = findViewById(R.id.txtAjout);
        txtVisu = findViewById(R.id.txtVisu);

        txtAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Creation_recette.class);
                startActivity(intent);
            }
        });

        txtVisu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(this,visualisation_recettes.class);
              //  startActivity(intent);
            }
        });


    }
}
