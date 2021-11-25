package com.example.notasstiky.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notasstiky.R;
import com.example.notasstiky.database.MyNoteDatabase;
import com.example.notasstiky.entities.MyNoteEntities;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Date;
import java.util.Locale;

public class AddNewNotes extends AppCompatActivity {

private EditText inputNoteTitle,inputNoteText;
private TextView textDateTime,saveNote;
private View indicator1,indicator2;
String selectedColor;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_notes);

        indicator1=findViewById(R.id.viewIndicator);
        indicator2=findViewById(R.id.viewIndicator2);
        saveNote=findViewById(R.id.btn_Save_Note);
        inputNoteText=findViewById(R.id.input_note_text);
        inputNoteTitle=findViewById(R.id.input_reminder_title);
        textDateTime=findViewById(R.id.textDateTime);

        selectedColor="#FF937B";
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        textDateTime.setText(
                new SimpleDateFormat("EEEE,dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(new Date())
        );

        bottonSheet();
        setViewColor();
    }

    private void setViewColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) indicator1.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedColor));

        GradientDrawable gradientDrawable2 = (GradientDrawable) indicator2.getBackground();
        gradientDrawable2.setColor(Color.parseColor(selectedColor));

    }

    private void saveNote() {
if (inputNoteTitle.getText().toString().trim().isEmpty()){
    Toast.makeText(this,"Titulo de Nota vacia",Toast.LENGTH_SHORT).show();
    return;
}else
    if (inputNoteText.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Texto de Nota vacia",Toast.LENGTH_SHORT).show();
            return;
        }
    final MyNoteEntities myNoteEntities = new MyNoteEntities();
    myNoteEntities.setTitle(inputNoteTitle.getText().toString());
        myNoteEntities.setNoteText(inputNoteText.getText().toString());
        myNoteEntities.setDateTime(textDateTime.getText().toString());
        myNoteEntities.setColor(selectedColor);
        class SaveNote extends AsyncTask<Void, Void,Void> {
            @Override
            protected Void doInBackground(Void... voids){
                MyNoteDatabase
                        .getMyNoteDatabase(getApplicationContext())
                        .notesDao()
                        .insertNote(myNoteEntities);
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
        new SaveNote().execute();
    }
    private void bottonSheet(){
        final LinearLayout linearLayout= findViewById(R.id.botton_layout);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        linearLayout.findViewById(R.id.bottom_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        final ImageView imgColor1= linearLayout.findViewById(R.id.imageColor1);
        final ImageView imgColor2= linearLayout.findViewById(R.id.imageColor2);
        final ImageView imgColor3= linearLayout.findViewById(R.id.imageColor3);
        final ImageView imgColor4= linearLayout.findViewById(R.id.imageColor4);
        /*escoger color del sheetDialog y poner palomita*/
        linearLayout.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#FF937B";
                imgColor1.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });

        linearLayout.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#FFFB7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });
        linearLayout.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#ADFF7B";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(R.drawable.ic_baseline_done_24);
                imgColor4.setImageResource(0);
                setViewColor();
            }
        });
        linearLayout.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColor = "#969CFF";
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(R.drawable.ic_baseline_done_24);
                setViewColor();
            }
        });
    }
}