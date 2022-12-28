package com.example.test.Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Custummer {

        private String id;
        private String nom;
        private Date date;
        private String adress ;

    public Custummer(String id, String nom,  String adress,Date date) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Custummer{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", adress='" + adress + '\'' +
                '}';
    }
}
