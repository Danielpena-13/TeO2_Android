package com.daniel13pe.navdrw_java.entities;

import java.io.Serializable;
import java.util.List;

public class Predio implements Serializable {

    private String thumbnailUrl;
    private int id;
    private String title;
    private List<Imagenes> imagenes = null;

    public Predio(){}

    public Predio(String thumbnailUrl, int id, String title, List<Imagenes> imagenes) {
        this.thumbnailUrl = thumbnailUrl;
        this.id = id;
        this.title = title;
        this.imagenes = imagenes;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Imagenes> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagenes> imagenes) {
        this.imagenes = imagenes;
    }
}
