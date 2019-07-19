package com.example.mesrecettes;

import java.util.Objects;

public class Users {

    private int id;
    private String identifiant;
    private String password;
    private String level;

    public Users(int id) {
        this.id = id;
    }

    public Users() {
    }

    public Users(int id, String identifiant, String password, String level) {
        this.id = id;
        this.identifiant = identifiant;
        this.password = password;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", identifiant='" + identifiant + '\'' +
                ", password='" + password + '\'' +
                ", level='" + level + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return getId() == users.getId() &&
                getIdentifiant().equals(users.getIdentifiant()) &&
                getPassword().equals(users.getPassword()) &&
                getLevel().equals(users.getLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdentifiant(), getPassword(), getLevel());
    }
}
