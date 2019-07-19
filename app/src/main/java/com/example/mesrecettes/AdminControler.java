package com.example.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class AdminControler extends AppCompatActivity {

    private DataBaseManager dbm;
    private Button btnIns,btnDel,btnUp,btnReset;
    private EditText txtIdentifiant,txtPassword,txtLevel;

    private MainActivity activity;
    private AlertDialog.Builder pop;

    private ListView lv;
    private List<Users> listUsers;

    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controler);

        txtIdentifiant=findViewById(R.id.identifiantToget);
        txtPassword=findViewById(R.id.passwordToget);
        txtLevel=findViewById(R.id.levelToget);

        activity = new MainActivity();
       // connex = new Connexion(activity);
        dbm = new DataBaseManager(this);
        createBtn();

        createListeUsers();

    }
    public void createListeUsers(){
        // instanciation de la liste de users
        listUsers = dbm.getAll();
        lv.setAdapter(new AdapterListUsers(this,listUsers));
    }
    public Users createSeeUser(){

        if(txtIdentifiant.getText().toString().matches("")||txtLevel.getText().toString().matches("")||txtPassword.getText().toString().matches("")) {
            pop = new AlertDialog.Builder(this);
            pop.setTitle(getApplicationContext().getResources().getString(R.string.pop_titre1));
            pop.setMessage("Veuillez remplir tous les champs");
            pop.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            pop.show();
        }

        else{
            String req = "SELECT * FROM users WHERE identifiant='" + txtIdentifiant.getText() + "'";
            Cursor cursor = dbm.getReadableDatabase().rawQuery(req, null);
           cursor.moveToNext();

             user = new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));

            Toast.makeText(getApplicationContext(), "" + user.getId() +  "", Toast.LENGTH_SHORT).show();

        }
        return user;
    }

    public void createBtn(){

 //Creation de la liste des users
        lv=findViewById(R.id.lvUsers);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

               Users user= (Users)lv.getAdapter().getItem(position);

               txtIdentifiant.setText(user.getIdentifiant());
               txtLevel.setText(user.getLevel());
               txtPassword.setText(user.getPassword());

            }
        });

 //Creation bouton supprimer
       btnDel=findViewById(R.id.btnDelete);
       btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSeeUser();

               dbm.Delete(createSeeUser().getId());

               Toast.makeText(getApplicationContext(), "delete ok", Toast.LENGTH_SHORT).show();
                createListeUsers();
            }
        });

//Creation du bouton insert
        btnIns=findViewById(R.id.btnInsert);
        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Users user = new Users(0,txtIdentifiant.getText().toString(),txtPassword.getText().toString(),txtLevel.getText().toString());
                    dbm.insert(user);
                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                createListeUsers();

            }
        });



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
