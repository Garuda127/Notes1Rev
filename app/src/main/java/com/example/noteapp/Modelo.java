package com.example.noteapp;

public class Modelo {

    private int imagenIcono;
    String titulo;
    String cuerpo;

    public Modelo(int imagenIcono, String titulo, String cuerpo) {
        this.imagenIcono = imagenIcono;
        this.titulo = titulo;
        this.cuerpo = cuerpo;

    }

    public Modelo() {

    }

    public int getImagenIcono() {
        return imagenIcono;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setImagenIcono(int imagenIcono) {
        this.imagenIcono = imagenIcono;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }


}



