package com.example.noteapp;

public class utilidades {


    public static final String TABLA_USUARIO = "user";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_USUARIO = "usuario";
    public static final String CAMPO_CONTRASEÑA = "contraseña";


   public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "" + " (" + CAMPO_NOMBRE + " TEXT, " +
           "" + CAMPO_USUARIO+" TEXT, " +
           "" + CAMPO_CONTRASEÑA + " TEXT)" ;



    public static final String TABLA_NOTAS = "notas";
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_DESCRIPCION = "desc";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_HORA = "hora";
    public static final String CAMPO_NOTA = "nota";
    public static final String CAMPO_ID = "_id";

    public static final String CREAR_TABLA_NOTAS =
            "CREATE TABLE " + TABLA_NOTAS + "" +
                    " ("+CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_TITULO + " TEXT, " +
                    "" + CAMPO_DESCRIPCION+" TEXT, " +
                    "" + CAMPO_FECHA+" TEXT, " +
                    "" + CAMPO_HORA+" TEXT, " +
                    "" + CAMPO_NOTA + " TEXT)" ;
}
