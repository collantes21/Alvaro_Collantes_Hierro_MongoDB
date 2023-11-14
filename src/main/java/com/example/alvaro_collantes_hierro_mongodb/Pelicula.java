package com.example.alvaro_collantes_hierro_mongodb;

import org.bson.types.ObjectId;

public class Pelicula {

    private ObjectId id;
    private String titulo;
    private String genero;
    private String cine;
    private String protagonista;

    public Pelicula() {
    }

    public Pelicula(ObjectId id, String titulo, String genero, String cine, String protagonista) {
        this.id=id;
        this.titulo = titulo;
        this.genero = genero;
        this.cine = cine;
        this.protagonista = protagonista;
    }

    public Pelicula(String titulo, String genero, String cine, String protagonista) {
        this.titulo = titulo;
        this.genero = genero;
        this.cine = cine;
        this.protagonista = protagonista;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCine() {
        return cine;
    }

    public void setCine(String cine) {
        this.cine = cine;
    }

    public String getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(String protagonista) {
        this.protagonista = protagonista;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
