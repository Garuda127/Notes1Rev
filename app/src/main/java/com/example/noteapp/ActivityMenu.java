package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMenu extends AppCompatActivity {

    Button add;
    Button acer;
    Button vn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        add=findViewById(R.id.btnAgregandoNota);
        acer=findViewById(R.id.btnAcerdaDe);
        vn=findViewById(R.id.btnVerNotas);
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent a = new Intent(ActivityMenu.this,AgregarNotas.class);
                startActivity(a);
            }
        });


        vn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent a = new Intent(ActivityMenu.this, Vista.class);
                startActivity(a);
            }
        });

        //Acerca de
        /*acer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b= new Intent(ActivityMenu.this,AcercaD.class);
                startActivity(b);
            }
        });*/
     }
}



























