package com.daniel13pe.navdrw_java.entities;

import java.io.Serializable;
import java.util.List;

public class Predio implements Serializable {

    private int id;
    private String nombrePredio;
    private String entidad;
    private List<Imagenes> imagenes = null;

    public Predio(){}

    public Predio(int id, String nombrePredio, String entidad, List<Imagenes> imagenes) {
        this.id = id;
        this.nombrePredio = nombrePredio;
        this.entidad = entidad;
        this.imagenes = imagenes;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombrePredio() { return nombrePredio; }

    public void setNombrePredio(String nombrePredio) { this.nombrePredio = nombrePredio; }

    public String getEntidad() { return entidad; }

    public void setEntidad(String entidad) { this.entidad = entidad; }

    public List<Imagenes> getImagenes() { return imagenes; }

    public void setImagenes(List<Imagenes> imagenes) { this.imagenes = imagenes; }
}
