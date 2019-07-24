package com.example.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.mesrecettes.controler.AdminControler;
import com.example.mesrecettes.controler.RecettesControler;
import com.example.mesrecettes.dataBaseManager.DataBaseManager;
import com.example.mesrecettes.dataBaseManager.DataBaseManager_Recette;
import com.example.mesrecettes.model.Users;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView btnConnexion, btnInscription;
    private EditText txtIdentifiant, txtPassword;

    //declaration de l'activité qui permet la gestion des users
    private DataBaseManager dbm;
    //declaration de l aclasse qui permet l'utilisation des fenetre de message pop
    private AlertDialog.Builder pop;

    private DataBaseManager_Recette dbmR;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//lors de son lancement la mainActivity active s le boutons + instancie un eliste de tous les users
        dbm = new DataBaseManager(this);
        init();
        listener();
        List<Users> liste = dbm.getAll();

        dbmR = new DataBaseManager_Recette(this);



    }
//methode qui declare les éléments de la page
    public void init(){
        btnConnexion = findViewById(R.id.btnConnexion);
        btnInscription = findViewById(R.id.btnInscription);
        txtIdentifiant = findViewById(R.id.txtnom);
        txtPassword = findViewById(R.id.txtcategorie);
    }


 //methode qui active le bouton connexion, 2cas de figure admin ou user.
    public void listener(){
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on verifie si le user est dans la bdd puis si il est admin ou pas.
                if( userExist()){
                    if(isAdmin()){
                        // ouvre une page de gestion des users
                        Intent intent = new Intent(getApplicationContext(), AdminControler.class);
                       startActivity(intent);

                    }
                    else{
                        //ouvre la pege d'activité de l'appli
                        Intent intent = new Intent(getApplicationContext(), RecettesControler.class);
                       startActivity(intent);
                    }

                }
            }
        });

        //methode qui active le bouton inscription, ouvre une boite mail avec un message préprogrammé.
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

//methode qui verifie que l'utilisateur qui veut se connecter existe
    public Boolean userExist (){
        boolean bool = false;

            //d'abord on verifie que l'identifiant ET le password existe grace aux methodes respectives
        if(identifiantExist(txtIdentifiant.getText().toString(),dbm) && passwordExist(txtPassword.getText().toString(),dbm) ){

            //on va chercher le user correspondant dans la bdd
            String req = "SELECT * FROM users WHERE identifiant='"+ txtIdentifiant.getText().toString() +"'  AND password ='"+  txtPassword.getText().toString() +"'";
            Cursor cursor = dbm.getReadableDatabase().rawQuery(req, null);
            if(cursor.moveToFirst()) {
                bool=true;

            }
            //si il n'existe pas message d'erreur
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
//verifie que l'identifiant existe (a condition que les champs soient remplis) dans la bdd sinon pop
    public Boolean identifiantExist(String identifiant, DataBaseManager dbm){
        boolean bool = false;

        String req = "SELECT * FROM users WHERE identifiant='"+identifiant+"'";
        Cursor cursor = dbm.getReadableDatabase().rawQuery(req, null);
        Log.i("ID",""+cursor.getCount());

       if(!identifiant.matches("")){
           if(cursor.getCount()>0){
               bool = true;
           }
           else{
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
       }else{
           Toast.makeText(this, "veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
       }

        return bool;
    }

    //verifie que le password existe dans la bdd (a condition que le champs soit rempli sinon pop
    public Boolean passwordExist(String password,DataBaseManager dbm){

        boolean bool = false;
        String req = "SELECT * FROM users WHERE password='"+password+"'";
        Cursor cursor =dbm.getReadableDatabase().rawQuery(req, null);

        if(!password.matches("")){
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
        }else{
            Toast.makeText(this, "veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }

        return bool;
    }

    //verifie si l'utilisateur est admin
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

