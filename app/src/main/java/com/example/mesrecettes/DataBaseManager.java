package com.example.mesrecettes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager extends SQLiteOpenHelper { //classe qui permet de gerer les entrées/sorties de la BDD

    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 2;
    private Context context;

    //lors de son instanciation le constructeur donne un nom et un numero de version à la bdd
    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //methode lancée lors de l'instanciation, crée la table avce des colonnes. ATTENTION!! la table ne cré que si on y insert uneentrée.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String req = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT,identifiant VARCHAR(255) NOT NULL,password VARCHAR(55) NOT NULL,level VARCHAR(55) NOT NULL)";

        db.execSQL(req);
        Log.i("DB","Création db ok");

    }

    // dans le cas ou l'on souhaite modifier la table il faut untiliser cette methode, y inserer la requete demandée et changer le numéro de version de la bdd.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //mthode qui permet d'insérer des entrées dans la bdd.
    public void insert(Users newUser){
        String identifiant = newUser.getIdentifiant();
        String password = newUser.getPassword();
        String level = newUser.getLevel();


        Users user = getOneId(newUser.getIdentifiant());

        //  en premier lieu on verifie que le user n'existe pas deja.
            if(user!=null){
                Toast.makeText(context, "l'utilisateur existe deja", Toast.LENGTH_SHORT).show();
            }else {
                String req = "INSERT INTO users(identifiant,password,level) VALUES('" + identifiant + "','" + password + "','" + level + "')";
                this.getReadableDatabase().execSQL(req);
            }
    }

    //methode pour supprimer un user
    public void Delete (int id){
        String req = "DELETE FROM users WHERE id= '"+id+"'";
        this.getReadableDatabase().execSQL(req);
        Toast.makeText(context, "l'utilisateur est supprimé", Toast.LENGTH_SHORT).show();
    }



    //methode pour sélectionner un user en fonction de'un id.
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

    //methode pour selectionner un utilisateur en fonction de son idenfiant.
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

    //methode qui sort une liste de tous les user.
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
