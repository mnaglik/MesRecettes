package com.example.mesrecettes.model;

public class Quantité {

    private int id;
    private int mesure;
    private String untité;

    public Quantité(int id, int mesure, String untité) {
        this.id = id;
        this.mesure = mesure;
        this.untité = untité;
    }

    public int getMesure() {
        return mesure;
    }

    public void setMesure(int mesure) {
        this.mesure = mesure;
    }

    public String getUntité() {
        return untité;
    }

    public void setUntité(String untité) {
        this.untité = untité;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
