package com.example.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView btnConnexion, btnInscription;
    private EditText txtIdentifiant, txtPassword;
    private DataBaseManager dbm;
    private AlertDialog.Builder pop;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connexion connexion = new Connexion(this);
        dbm = new DataBaseManager(this);
        init();
        listener();

       List<Users> liste = dbm.getAll();

        for (Users user: liste){
            Log.i("getall", ""+user.getIdentifiant());
        }



    }

    public void init(){
        btnConnexion = findViewById(R.id.btnConnexion);
        btnInscription = findViewById(R.id.btnInscription);
        txtIdentifiant = findViewById(R.id.txtIdentifiant);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void listener(){
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( userExist()){
                    if(isAdmin()){

                        Intent intent = new Intent(getApplicationContext(),AdminControler.class);
                       startActivity(intent);

                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),MesRecettes.class);
                       startActivity(intent);
                    }

                }
            }
        });

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"naglikmarie@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "demande d'inscription");
                i.putExtra(Intent.EXTRA_TEXT," donner un identifiant et un mot de passe");
                startActivity(Intent.createChooser(i, "Titre:"));
            }
        });

    }


    public Boolean userExist (){
        boolean bool = false;

        if(identifiantExist(txtIdentifiant.getText().toString(),dbm) && passwordExist(txtPassword.getText().toString(),dbm) ){


            String req = "SELECT * FROM users WHERE identifiant='"+ txtIdentifiant.getText().toString() +"'  AND password ='"+  txtPassword.getText().toString() +"'";
            Cursor cursor = dbm.getReadableDatabase().rawQuery(req, null);
            if(cursor.moveToFirst()) {
                bool=true;

            }
            else{
                pop = new AlertDialog.Builder(this);
                this.pop.setTitle(getResources().getString(R.string.pop_titre1));
                this.pop.setMessage("l'idenfiant et le password ne correspondent pas.");
                this.pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                this.pop.show();

            }
        }
        return bool;
    }

    public Boolean identifiantExist(String identifiant, DataBaseManager dbm){
        boolean bool = false;

        String req = "SELECT * FROM users WHERE identifiant='"+identifiant+"'";
        Cursor cursor = dbm.getReadableDatabase().rawQuery(req, null);
        Log.i("ID",""+cursor.getCount());

        if(cursor.getCount()>0){
            bool = true;
            Toast.makeText(getApplicationContext(), "lidebtifiant existe ", Toast.LENGTH_SHORT).show();
        }else{
            pop = new AlertDialog.Builder(this);
            pop.setTitle(getResources().getString(R.string.pop_titre1));
            pop.setMessage(getResources().getString(R.string.pop_message1));
            pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            pop.show();
        }

        return bool;
    }

    public Boolean passwordExist(String password,DataBaseManager dbm){

        boolean bool = false;
        String req = "SELECT * FROM users WHERE password='"+password+"'";
        Cursor cursor =dbm.getReadableDatabase().rawQuery(req, null);

        if (cursor.moveToFirst()){
            bool=true;
        }
        else{
            pop = new AlertDialog.Builder(this);
            this.pop.setTitle(this.getResources().getString(R.string.pop_titre1));
            this.pop.setMessage("ce password n'existe pas");
            this.pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            this.pop.show();

        }
        return bool;
    }

    public Boolean isAdmin(){
        boolean bool = false;

        String req = "SELECT * FROM users WHERE identifiant = '"+txtIdentifiant.getText().toString()+"'";
        Cursor cursor = dbm.getReadableDatabase().rawQuery(req, null);
        cursor.moveToFirst();
        Users user = new Users (cursor.getInt(0 ),cursor.getString(1),cursor.getString(2),cursor.getString(3));

        if(user.getLevel().matches("admin")){
            bool=true;

        }

        return bool;
    }


    }

