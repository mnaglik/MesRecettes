package com.example.mesrecettes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DataBaseManager_Recette extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recettes";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private String titre;

    public DataBaseManager_Recette(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String req = "CREATE TABLE recettes (id INTEGER PRIMARY KEY AUTOINCREMENT,titre TEXTE(255) ) ";

        db.execSQL(req);
        Log.i("DB","Création db ok");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void insert(String titre){

        String req = "INSERT INTO recettes (titre) VALUES ('"+titre+"')";
       this.getReadableDatabase().execSQL(req);
        Log.i("insert","ok ");


    }


}
