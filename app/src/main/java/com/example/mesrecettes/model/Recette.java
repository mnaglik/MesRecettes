package com.example.mesrecettes.model;

import java.util.List;

public class Recette {


    // une recette à un nom est un id.
    private int id;
    private String nom;
    private List<Ajout> listAjout;


    public Recette(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Ajout> getListAjout() {
        return listAjout;
    }

    public void setListAjout(List<Ajout> listAjout) {
        this.listAjout = listAjout;
    }

    @Override
    public String toString() {
        return "Recette{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
