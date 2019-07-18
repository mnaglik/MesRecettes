package com.example.mesrecettes;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controler);

        dbm = new DataBaseManager(this);
        createBtn();





    }

    public void createBtn(){
        txtIdentifiant=findViewById(R.id.identifiantToget);
        txtPassword=findViewById(R.id.passwordToget);
        txtLevel=findViewById(R.id.levelToget);



       btnDel=findViewById(R.id.btnDelete);
       btnDel.setOnClickListener(new View.OnClickListener() {
        Users user = new Users(0,txtIdentifiant.getText().toString(),txtPassword.getText().toString(),txtLevel.getText().toString());
            @Override
            public void onClick(View view) {
                String req = "SELECT * FROM users WHERE identifiant='"+txtIdentifiant.getText()+"'";
                Cursor cursor = dbm.getReadableDatabase().rawQuery(req,null);
                cursor.moveToNext();
                Users user = new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                dbm.Delete(user.getId());
                Toast.makeText(getApplicationContext(), ""+user.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        btnIns=findViewById(R.id.btnInsert);
        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users user = new Users(0,txtIdentifiant.getText().toString(),txtPassword.getText().toString(),txtLevel.getText().toString());
                dbm.insert(user);
                Toast.makeText(getApplicationContext(), "insert ok", Toast.LENGTH_SHORT).show();
            }
        });

        btnUp=findViewById(R.id.btnUpdate);
        /*btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbm.update(user);
                Toast.makeText(getApplicationContext(), "update ok", Toast.LENGTH_SHORT).show();
            }
        });*/

        btnReset=findViewById(R.id.btnReset);
       /* btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPassword.setText("");
                txtIdentifiant.setText("");
                txtLevel.setText("");
                Toast.makeText(getApplicationContext(), "reset ok", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
