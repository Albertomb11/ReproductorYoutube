package com.alberto.reproductoryoutube.model;


public class Video {
    private String titulo;
    private String autor;
    private String imagen;
    private String id;

    public Video() {

    }

    public Video(String titulo, String autor, String imagen, String id) {
        this.titulo = titulo;
        this.autor = autor;
        this.imagen = imagen;
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Titulo: " + getTitulo() + "-" + getAutor();
    }
}
