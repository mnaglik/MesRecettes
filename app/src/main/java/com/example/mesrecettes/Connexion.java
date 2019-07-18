package com.example.mesrecettes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Connexion {

    private TextView btnConnexion, btnInscription;
    private EditText txtIdentifiant, txtPassword;
    private MainActivity activity;
    private DataBaseManager dbm;

    public Connexion(MainActivity activity) {
        this.activity= activity;
        this.dbm=new DataBaseManager(activity.getApplicationContext());
    }

    public void init(){
        btnConnexion = activity.findViewById(R.id.btnConnexion);
        btnInscription = activity.findViewById(R.id.btnInscription);
        txtIdentifiant = activity.findViewById(R.id.txtIdentifiant);
        txtPassword = activity.findViewById(R.id.txtPassword);
    }

    public void listener(){
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( userExist()){
                    if(isAdmin()){
                       Intent intent = new Intent(activity.getApplicationContext(),AdminControler.class);
                        activity.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(activity.getApplicationContext(),MesRecettes.class);
                        activity.startActivity(intent);
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
                activity.startActivity(Intent.createChooser(i, "Titre:"));
            }
        });

    }


    public Boolean userExist (){
        boolean bool = false;

        if(identifiantExist()&& passwordExist()){

            String ident = txtIdentifiant.getText().toString();
            String pass = txtPassword.getText().toString();
            String req = "SELECT * FROM users WHERE identifiant='"+ ident +"'  AND password ='"+ pass +"'";
            Cursor cursor =dbm.getReadableDatabase().rawQuery(req, null);
            if(cursor.moveToFirst()) {
                bool=true;
            }
            else{AlertDialog.Builder pop = new AlertDialog.Builder(activity);
                pop.setTitle(activity.getResources().getString(R.string.pop_titre1));
                pop.setMessage("l'idenfiant et le password ne correspondent pas.");
                pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                pop.show();

            }
        }
        return bool;
    }

    public Boolean identifiantExist(){
        boolean bool = false;
        String identifiant = txtIdentifiant.getText().toString();
        String req = "SELECT * FROM users WHERE identifiant='"+identifiant+"'";
        Cursor cursor =dbm.getReadableDatabase().rawQuery(req, null);

        if (cursor.moveToNext()) {

            bool = true;
        }
        else{
            AlertDialog.Builder pop = new AlertDialog.Builder(activity);
            pop.setTitle(activity.getResources().getString(R.string.pop_titre1));
            pop.setMessage(activity.getResources().getString(R.string.pop_message1));
            pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            pop.show();

        }
        return bool;
    }

    public Boolean passwordExist(){
        String password = txtPassword.getText().toString();
        boolean bool = false;
        String req = "SELECT * FROM users WHERE password='"+password+"'";
        Cursor cursor =dbm.getReadableDatabase().rawQuery(req, null);

        if (cursor.moveToFirst()){
            bool=true;
        }
        else{
            AlertDialog.Builder pop = new AlertDialog.Builder(activity);
            pop.setTitle(activity.getResources().getString(R.string.pop_titre1));
            pop.setMessage("ce password n'existe pas");
            pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            pop.show();

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
