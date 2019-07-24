package com.example.mesrecettes.model;

public class Ajout {

    private int id;
    private Ingredient ingredient;
    private Quantité quantité;

    public Ajout(int id, Ingredient ingredient, Quantité quantité) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantité = quantité;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Quantité getQuantité() {
        return quantité;
    }

    public void setQuantité(Quantité quantité) {
        this.quantité = quantité;
    }

    @Override
    public String toString() {
        return "Ajout{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                ", quantité=" + quantité +
                '}';
    }
}
