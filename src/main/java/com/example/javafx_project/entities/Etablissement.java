package com.example.javafx_project.entities;

import java.io.Serializable;

public class Etablissement implements Serializable {
    private int id;
    private String type;

    private String nom;

    public Etablissement(int id, String type, String nom) {
        this.id = id;
        this.type = type;
        this.nom = nom;
    }

    public Etablissement() {
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
