package com.example.noteapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AgregarNotas extends AppCompatActivity {
    TextView te;
    DatePickerDialog datePickerDialog;
    Button fec;
    Button fec2;
    TextView te2;
    TimePickerDialog timePickerDialog;

    Button agre;
    EditText titu;
    EditText desc;
    EditText nott;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarnota);
        fec=(Button)findViewById(R.id.buttonfech);
        te=(TextView)findViewById(R.id.fech);
        te2=(TextView)findViewById(R.id.fech2);
        fec2=(Button)findViewById(R.id.fech22);
        agre=findViewById(R.id.btnAgregar);

        titu=findViewById(R.id.editTextTextPersonName4);
        desc= findViewById(R.id.editTextTextPersonName5);
        nott= findViewById(R.id.txtNota);



        fec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cc2=Calendar.getInstance();
                int hour=cc2.get(Calendar.HOUR);
                int minute=cc2.get(Calendar.MINUTE);
                timePickerDialog= new TimePickerDialog(AgregarNotas.this, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        te2.setText(i+":"+i1);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, i);
                        c.set(Calendar.MINUTE,i1);
                        c.set(Calendar.SECOND,0);
                        startalamr(c);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });


        fec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cc=Calendar.getInstance();
                int year = cc.get(Calendar.YEAR);
                int mont=cc.get(Calendar.MONTH);
                int day=cc.get(Calendar.DAY_OF_MONTH);

                datePickerDialog= new DatePickerDialog(AgregarNotas.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        te.setText(i+"/"+(i1+1)+"/"+i2);
                    }
                },year, mont,day);
                datePickerDialog.show();
            }
        });


        agre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnota();
                Intent a= new Intent(AgregarNotas.this,MainActivity.class);
                startActivity(a);
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  void startalamr(Calendar c){
        AlarmManager alarmManager =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }


    private void addnota() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "db_usuarios", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_TITULO, titu.getText().toString());
        values.put(utilidades.CAMPO_DESCRIPCION, desc.getText().toString());
        values.put(utilidades.CAMPO_FECHA, te.getText().toString());
        values.put(utilidades.CAMPO_HORA, te2.getText().toString());
        values.put(utilidades.CAMPO_NOTA, nott.getText().toString());


        Long nom = db.insert(utilidades.TABLA_NOTAS, utilidades.CAMPO_TITULO, values);
        Toast.makeText(getApplicationContext(),"Nota creada",Toast.LENGTH_SHORT).show();
        db.close();
    }
}