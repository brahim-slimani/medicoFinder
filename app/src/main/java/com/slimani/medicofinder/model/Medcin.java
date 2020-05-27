package com.slimani.medicofinder.model;


public class Medcin {
    private int id;
    private String nom;
    private String specialite;
    private String longitude;
    private String latitude;
    private String tel;

    public Medcin(int id, String nom, String specialite, String longitude, String latitude) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Medcin(int id, String nom, String specialite, String longitude, String latitude, String tel) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tel = tel;
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
