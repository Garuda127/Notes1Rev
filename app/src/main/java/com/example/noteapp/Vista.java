package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Vista extends AppCompatActivity {
    ArrayList<Modelo> modeloList;
    RecyclerView recyclerNotas;
    private Adaptador.RecyclerViewClickListener listener;


    ConexionSQLiteHelper coon;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vista_recycler_notas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        coon = new ConexionSQLiteHelper(getApplicationContext(), "db_usuarios",null,1);
        modeloList = new ArrayList<>();
        recyclerView=findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        setOnClickListener();
        Adaptador adaptador = new Adaptador(modeloList);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.getChildAdapterPosition(view);
                view.findViewById(R.id.textTitle);

                String info=modeloList.get(recyclerView.getChildAdapterPosition(view)).getTitulo().toString();
                String info2=modeloList.get(recyclerView.getChildAdapterPosition(view)).getCuerpo().toString();
                Intent bf = new Intent(Vista.this,details.class);
                bf.putExtra("pasa", info);
                bf.putExtra("pasa2", info2);
                startActivity(bf);

            }
        });

        recyclerView.setAdapter(adaptador);
        ConsultarListaNotas();
        adaptador.notifyDataSetChanged();

    }

    private void setOnClickListener() {
        listener = new Adaptador.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), details.class);

                intent.putExtra("titulo" , modeloList.get(position).getTitulo());
                intent.putExtra("cuerpo", modeloList.get(position).getCuerpo());
                intent.putExtra("imageIcon" , modeloList.get(position).getImagenIcono());
                startActivity(intent);
            }
        };
    }

    private void ConsultarListaNotas() {

        SQLiteDatabase db =coon.getReadableDatabase();

        Modelo modelo = null;

        Cursor cursor = db.rawQuery("SELECT * FROM  " + utilidades.TABLA_NOTAS,null);

        while (cursor.moveToNext()){

            modelo = new Modelo();
            modelo.setImagenIcono(R.drawable.ic_baseline_sticky_note_2_24);
            modelo.setTitulo(cursor.getString(0));
            modelo.setCuerpo(cursor.getString(1));
            modeloList.add(modelo);
        }
    }
    private void BorrarListaNotas() {

        SQLiteDatabase db =coon.getReadableDatabase();

        Modelo modelo = new Modelo();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + utilidades.TABLA_NOTAS,null);




    }

}