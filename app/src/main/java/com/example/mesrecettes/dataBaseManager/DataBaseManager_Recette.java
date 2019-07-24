package com.example.mesrecettes.dataBaseManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mesrecettes.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager_Recette extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recettes";
    private static final int DATABASE_VERSION = 2;
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

        onUpgrade(db,1,2);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String req = "CREATE TABLE ingredients (id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXTE(255),categorie TEXTE(255) ) ";
        db.execSQL(req);
        Log.i("DB","Création ingredient ok");

        String req2 = "CREATE TABLE ingredients_recettes (id INTEGER PRIMARY KEY AUTOINCREMENT,id_recettes INTEGER,id_ingredients INTEGER,qte INTEGER, mesure TEXTE(55) ) ";
        db.execSQL(req2);
        Log.i("DB","Création ingredients_recettes ok");

    }

    public void insertRecette(String titre){

        String req = "INSERT INTO recettes (titre) VALUES ('"+titre+"')";
       this.getReadableDatabase().execSQL(req);
        Log.i("insert","ok ");


    }
    public void insertIngregient(String nom, String categorie){

        String req = "INSERT INTO ingredients (nom,categorie) VALUES ('"+nom+"','"+categorie+"')";
        this.getReadableDatabase().execSQL(req);
        Log.i("inserting","ok ");


    }
    public List<Ingredient> getAllIngredient
            (){

        List<Ingredient> ingList = new ArrayList<>();
        String req = "SELECT * FROM ingredients ";
        Cursor cursor = this.getReadableDatabase().rawQuery(req,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Ingredient ingredient = new Ingredient (cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            ingList.add(ingredient);
            cursor.moveToNext();
        }
        cursor.close();
        return ingList;


    }

}
