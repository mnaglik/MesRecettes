package com.example.mesrecettes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 2;
    private Context context;

    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String req = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT,identifiant VARCHAR(255) NOT NULL,password VARCHAR(55) NOT NULL,level VARCHAR(55) NOT NULL)";

        db.execSQL(req);
        Log.i("DB","Création db ok");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Users newUser){
        String identifiant = newUser.getIdentifiant();
        String password = newUser.getPassword();
        String level = newUser.getLevel();


        Users user = getOneId(newUser.getIdentifiant());

        Log.i("TestUser",""+user);

        if(user!=null){
            Toast.makeText(context, "l'utilisateur existe deja", Toast.LENGTH_SHORT).show();
        }else {
            String req = "INSERT INTO users(identifiant,password,level) VALUES('" + identifiant + "','" + password + "','" + level + "')";
            this.getReadableDatabase().execSQL(req);
        }
    }

    public void Delete (int id){
        String req = "DELETE FROM users WHERE id= '"+id+"'";
        this.getReadableDatabase().execSQL(req);
        Toast.makeText(context, "l'utilisateur est supprimé", Toast.LENGTH_SHORT).show();
    }




    public Users getOne (int id){
        Users user ;
        String req = "SELECT * FROM users WHERE id='"+id+"' ";
        Cursor cursor =this.getReadableDatabase().rawQuery(req, null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            user = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }else{
            user = null;
        }

        return user;

    }

    public Users getOneId (String identifiant){
        Users user ;
        String req = "SELECT * FROM users WHERE identifiant='"+identifiant+"' ";
        Cursor cursor =this.getReadableDatabase().rawQuery(req, null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            user = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }else{
            user = null;
        }

        return user;

    }
    public List<Users> getAll (){
        List<Users> users = new ArrayList<>();
        String req = "SELECT * FROM users ";
        Cursor cursor = this.getReadableDatabase().rawQuery(req,null);
        cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    Users user = new Users (cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                    users.add(user);
                    cursor.moveToNext();
                }
        cursor.close();
        return users;
    }

}
