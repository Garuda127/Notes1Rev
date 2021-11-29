package com.example.notasstiky.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.notasstiky.R;
import com.example.notasstiky.alarm.WorkManagerNoti;
import com.example.notasstiky.database.MyNoteDatabase;
import com.example.notasstiky.entities.MyNoteEntities;
import com.example.notasstiky.entities.MyReminderEntities;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AddNewReminderActivity extends AppCompatActivity {
    private EditText title;
    private TextView textDateTime,textFecha,textHora;
    private View view;
    private LinearLayout linearLayoutHora,linearLayoutFecha;

    private MyReminderEntities alreadyAvailableReminder;
    String selectedReminderColor;
    private AlertDialog alertDialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reminder);
        TextView saveNote=findViewById(R.id.btn_save_reminder);
        view = findViewById(R.id.View_reminder);
        title=findViewById(R.id.input_reminder_title);
        textDateTime= findViewById(R.id.textDateTime);
        textFecha=findViewById(R.id.textViewFecha);
        textHora=findViewById(R.id.textViewHora);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        selectedReminderColor="#FF937B";
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReminder();
            }
        });
        textDateTime.setText(
                new SimpleDateFormat("EEEE,dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
        if(getIntent().getBooleanExtra("updateOrView",false)){
            alreadyAvailableReminder = (MyReminderEntities)  getIntent().getSerializableExtra("myNotes");
            setViewUpdate();

        }

        bottomSheet();
        setViewColor();
    }
    //setea los datos de la bd a el fragment
    private void setViewUpdate() {
        title.setText(alreadyAvailableReminder.getTitle());
        textDateTime.setText(alreadyAvailableReminder.getDateTime());


    }
    private void showDeleteDialog() {
        if (alertDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewReminderActivity.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_delete_note,
                    (ViewGroup) findViewById(R.id.layoutDeleteNote_Container));

            builder.setView(view);
            alertDialog = builder.create() ;
            if (alertDialog.getWindow() != null){
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    class DeleteNoteTask extends  AsyncTask<Void,Void,Void>{

                        @Override
                        protected Void doInBackground(Void... voids) {

                            MyNoteDatabase.getMyNoteDatabase(getApplicationContext()).notesDao().deleteReminder(alreadyAvailableReminder);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            super.onPostExecute(unused);

                            Intent intent = new Intent();
                            intent.putExtra("isReminderDeleted",true);
                            setResult(RESULT_OK,intent);
                            finish();

                        }
                    }

                    new DeleteNoteTask().execute();
                }
            });

            view.findViewById(R.id.textCancelNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        }

        alertDialog.show();
    }

    private void saveReminder() {
        if (title.getText().toString().trim().isEmpty()){
            Toast.makeText(this,R.string.Title_Empty,Toast.LENGTH_SHORT).show();
            return;
        }
        final MyReminderEntities myNoteEntities = new MyReminderEntities();
        myNoteEntities.setTitle(title.getText().toString());
        myNoteEntities.setDateTime(textDateTime.getText().toString().concat(textHora.getText().toString()));
        myNoteEntities.setTerminado(false);
        myNoteEntities.setDateTimeComplete(textFecha.getText().toString());
        myNoteEntities.setColor(selectedReminderColor);

        if (alreadyAvailableReminder !=null){
            myNoteEntities.setId(alreadyAvailableReminder.getId());
        }

        class SaveReminder extends AsyncTask<Void, Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                MyNoteDatabase
                        .getMyNoteDatabase(getApplicationContext())
                        .notesDao()
                        .insertReminder(myNoteEntities);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new SaveReminder().execute();

        String tag = generatekey();
        Long alerttime=calendar.getTimeInMillis() - System.currentTimeMillis();
        int random=(int)(Math.random() * 50+1);
        String detalle = title.getText().toString();
        Data data = GuardarData("Tienes Una Tarea Pendiente",detalle,random);
        WorkManagerNoti.SaveNoti(alerttime,data,tag);

    }

    private void setViewColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedReminderColor));
    }

    private void bottomSheet() {
        final LinearLayout linearLayout= findViewById(R.id.reminder_bottom);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        linearLayout.findViewById(R.id.bottom_text_reminder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        final ImageView imgColor1= linearLayout.findViewById(R.id.imageReminderColor1);
        final ImageView imgColor2= linearLayout.findViewById(R.id.imageReminderColor2);
        final ImageView imgColor3= linearLayout.findViewById(R.id.imageReminderColor3);
        final ImageView imgColor4= linearLayout.findViewById(R.id.imageReminderColor4);
        /*escoger color del sheetDialog y poner palomita*/
        linearLayout.findViewById(R.id.viewReminderColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedReminderColor = "#FF937B";
                imgColor1.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewReminderColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedReminderColor = "#FFFB7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });
        linearLayout.findViewById(R.id.viewReminderColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedReminderColor = "#ADFF7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });
        linearLayout.findViewById(R.id.viewReminderColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedReminderColor = "#969CFF";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(R.drawable.ic_baseline_done_24);
                setViewColor();
            }
        });

        if (alreadyAvailableReminder != null && alreadyAvailableReminder.getColor()!= null && !alreadyAvailableReminder.getColor().trim().isEmpty()){

            switch (alreadyAvailableReminder.getColor()){
                case "#FFFB7B":
                    linearLayout.findViewById(R.id.viewReminderColor2).performClick();
                    break;
                case "#ADFF7B":
                    linearLayout.findViewById(R.id.viewReminderColor3).performClick();
                    break;
                case "#969CFF":
                    linearLayout.findViewById(R.id.viewReminderColor4).performClick();
                    break;
            }
        }
        if (alreadyAvailableReminder != null){
            linearLayout.findViewById(R.id.delete_img1).setVisibility(View.VISIBLE);
            linearLayout.findViewById(R.id.delete_img1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    showDeleteDialog();
                }
            });
        }

    }





    //Calendario, hora y Alarma
    private int min,hora,dia,mes,anio;
    Calendar calendar= Calendar.getInstance();
    Calendar actual= Calendar.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void abrir_calendario(View view) {



        anio = actual.get(Calendar.YEAR);
        mes= actual.get(Calendar.MONTH);
        dia=actual.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                calendar.set(Calendar.DAY_OF_MONTH,d);
                calendar.set(Calendar.MONTH,m);
                calendar.set(Calendar.YEAR,y);

                SimpleDateFormat format = new   SimpleDateFormat("EEEE,dd MMMM yyyy", Locale.getDefault());
                String strDate=format.format(calendar.getTime());
                textFecha.setText(strDate);
            }
        },anio,mes,dia);//Date
        datePickerDialog.show();



    }

    public void abrir_reloj(View view) {

        hora = actual.get(Calendar.HOUR_OF_DAY);
        min=actual.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog= new TimePickerDialog(AddNewReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int h, int m) {
                calendar.set(Calendar.HOUR_OF_DAY,h);
                calendar.set(Calendar.MINUTE,m);

                textHora.setText(String.format("%02d:%02d",h,m));
            }
        },hora,min, false);
        timePickerDialog.show();
    }

    private void eliminarNoti(String tag){
        WorkManager.getInstance(this).cancelAllWorkByTag(tag);
        Toast.makeText(AddNewReminderActivity.this, R.string.Alarm_delete, Toast.LENGTH_SHORT).show();
    }

    private String generatekey(){
        return UUID.randomUUID().toString();
    }

    private Data GuardarData(String titulo, String detalle, int id_noti){
        return  new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("id_noti",id_noti).build();
    }
}