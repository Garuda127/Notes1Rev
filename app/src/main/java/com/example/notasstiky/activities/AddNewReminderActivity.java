package com.example.notasstiky.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notasstiky.R;
import com.example.notasstiky.database.MyNoteDatabase;
import com.example.notasstiky.entities.MyNoteEntities;
import com.example.notasstiky.entities.MyReminderEntities;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Date;
import java.util.Locale;

public class AddNewReminderActivity extends AppCompatActivity {
    private EditText title;
    private TextView textDateTime,textFecha,textHora;
    private View view;
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

    private void saveReminder() {
        if (title.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Titulo de Nota vacia",Toast.LENGTH_SHORT).show();
            return;
    }
        final MyReminderEntities myNoteEntities = new MyReminderEntities();
        myNoteEntities.setTitle(title.getText().toString());
        myNoteEntities.setDateTime(textDateTime.getText().toString());
        myNoteEntities.setTerminado(false);
        myNoteEntities.setDateTimeComplete("10/10/10");
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
                            intent.putExtra("isNoteDeleted",true);
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


}