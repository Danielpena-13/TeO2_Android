package com.daniel13pe.navdrw_java.entities;

public class Imagenes {

    private int id;
    private String direccionBucket;

    public Imagenes(int id, String direccionBucket) {
        this.id = id;
        this.direccionBucket = direccionBucket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionBucket() { return direccionBucket; }

    public void setDireccionBucket(String direccionBucket) {
        this.direccionBucket = direccionBucket;
    }
}
