package com.example.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminControler extends AppCompatActivity {

    private DataBaseManager dbm;
    private Button btnIns,btnDel,btnUp,btnReset;
    private EditText txtIdentifiant,txtPassword,txtLevel;
    private Connexion connex;
    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controler);

        txtIdentifiant=findViewById(R.id.identifiantToget);
        txtPassword=findViewById(R.id.passwordToget);
        txtLevel=findViewById(R.id.levelToget);

       // activity = new MainActivity();
       // connex = new Connexion(activity);
        dbm = new DataBaseManager(this);
        createBtn();

        //createSeeUser();

    }
    public Users createSeeUser(){
        String req = "SELECT * FROM users WHERE identifiant='"+txtIdentifiant.getText()+"'";
        Cursor cursor = dbm.getReadableDatabase().rawQuery(req,null);
        cursor.moveToNext();
        Users user = new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        Toast.makeText(getApplicationContext(), ""+user.getId()+" "+user.getIdentifiant()+" "+user.getPassword()+" "+user.getLevel()+"", Toast.LENGTH_SHORT).show();
        return user;
    }

    public void createBtn(){

       btnDel=findViewById(R.id.btnDelete);
       btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // dbm.Delete(createSeeUser().getId());
                Toast.makeText(getApplicationContext(), ""+createSeeUser().getId(), Toast.LENGTH_SHORT).show();
            }
        });

        btnIns=findViewById(R.id.btnInsert);
        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(connex.identifiantExist("sebastien")){
                   // Users user = new Users(0,txtIdentifiant.getText().toString(),txtPassword.getText().toString(),txtLevel.getText().toString());
                   // dbm.insert(user);
                    Toast.makeText(getApplicationContext(), "identifiant inconnu", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder pop = new AlertDialog.Builder(activity);
                    pop.setTitle(activity.getResources().getString(R.string.pop_titre1));
                    pop.setMessage("cet identifiant existe deja");
                    pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    pop.show();
                }*/

            }
        });

        btnUp=findViewById(R.id.btnUpdate);
      /*  btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbm.update(createSeeUser());
                Toast.makeText(getApplicationContext(), "update ok", Toast.LENGTH_SHORT).show();
            }
        });*/

        btnReset=findViewById(R.id.btnReset);
       btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPassword.setText("");
                txtIdentifiant.setText("");
                txtLevel.setText("");

            }
        });
    }
}
