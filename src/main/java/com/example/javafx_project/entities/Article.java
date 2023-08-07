package com.example.javafx_project.entities;

import javafx.scene.control.Button;

import java.io.Serializable;
import java.sql.Date;

public class Article implements Serializable {
    private Button button;

    private int id;
    private String categorie;

    private String designation;
    private int quantite;
    private Date datedajt;

    public Article() {
    }

    public Article(int id, String categorie, String designation, int quantite, Date datedajt) {
        this.id = id;
        this.categorie = categorie;
        this.designation = designation;
        this.quantite = quantite;
        this.datedajt = datedajt;
    }

    public Article(String categorie, String designation, int quantite, Date datedajt) {
        this.categorie = categorie;
        this.designation = designation;
        this.quantite = quantite;
        this.datedajt = datedajt;
    }

    public int getId() {
        return id;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getDesignation() {
        return designation;
    }

    public int getQuantite() {
        return quantite;
    }

    public Date getDatedajt() {
        return datedajt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setDatedajt(Date datedajt) {
        this.datedajt = datedajt;
    }
    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", categorie='" + categorie + '\'' +
                ", designation='" + designation + '\'' +
                ", quantite=" + quantite +
                ", datedajt=" + datedajt +
                '}';
    }
}
