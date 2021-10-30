package com.example.noteapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    final String CREAR_TABLA_USUARIO="CREATE TABLE usuarios(nombre TEXT,usuario TEXT, contraseña TEXT)";
    final String CREAR_TABLA_NOTAS="CREATE TABLE notas(titulo TEXT, desc TEXT, fecha TEXT, hora TEXT, nota TEXT)";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name,
                                @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(utilidades.CREAR_TABLA_NOTAS);
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS notas");
        onCreate(db);

    }


    public Cursor consultauser(String usu,String pass)throws SQLException{

        Cursor mcursor=null;
        mcursor=this.getReadableDatabase().query("user",new String[]{"nombre",
                "usuario","contraseña"},"usuario like '"+usu+"' "+
                "and contraseña like '"+pass+"' ",null,null,null,null);
        return  mcursor;
    }
}
