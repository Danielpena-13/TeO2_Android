package com.daniel13pe.navdrw_java.entities;

public class Imagenes {

    private int id;
    private String nombreImage;

    public Imagenes(int id, String nombreImage) {
        this.id = id;
        this.nombreImage = nombreImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreImage() {
        return nombreImage;
    }

    public void setNombreImage(String nombreImage) {
        this.nombreImage = nombreImage;
    }
}
