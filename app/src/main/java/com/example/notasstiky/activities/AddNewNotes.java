package com.example.notasstiky.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

public class AddNewNotes extends AppCompatActivity {

    private EditText inputNoteTitle,inputNoteText;
    private TextView textDateTime,saveNote;
    private View indicator1,indicator2;
    String selectedColor;

    ImageView addImg;
    private String SelectdImg;

    private MyNoteEntities alreadyAvailableNote;

    public static final int STORAGE_PERMISSION = 1;
    public static final int SELECT_IMG = 1;
    private AlertDialog alertDialog;

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
        addImg=findViewById(R.id.image_note);

        selectedColor="#FF937B";
        SelectdImg="";

        if(getIntent().getBooleanExtra("updateOrView",false)){
            alreadyAvailableNote = (MyNoteEntities)  getIntent().getSerializableExtra("myNotes");
            setViewUpdate();

        }

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

    private void setViewUpdate() {
        inputNoteTitle.setText(alreadyAvailableNote.getTitle());
        inputNoteText.setText(alreadyAvailableNote.getNoteText());
        textDateTime.setText(alreadyAvailableNote.getDateTime());
        if(alreadyAvailableNote.getImagePath() != null && !alreadyAvailableNote.getImagePath().trim().isEmpty()){

            addImg.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote.getImagePath()));
            addImg.setVisibility(View.VISIBLE);
            findViewById(R.id.img_remove).setVisibility(View.VISIBLE);
            SelectdImg = alreadyAvailableNote.getImagePath();
        }
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
        myNoteEntities.setImagePath(SelectdImg);

        if(alreadyAvailableNote !=null){
            myNoteEntities.setId(alreadyAvailableNote.getId());
        }


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

        ////////////////////////////////////
        if (alreadyAvailableNote != null && alreadyAvailableNote.getColor()!= null && !alreadyAvailableNote.getColor().trim().isEmpty()){

            switch (alreadyAvailableNote.getColor()){
                case "#FFFB7B":
                    linearLayout.findViewById(R.id.viewColor2).performClick();
                    break;
                case "#ADFF7B":
                    linearLayout.findViewById(R.id.viewColor3).performClick();
                    break;
                case "#969CFF":
                    linearLayout.findViewById(R.id.viewColor4).performClick();
                    break;
            }
        }

        //////////////////////////////////////////////////Agregar img
        linearLayout.findViewById(R.id.add_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            AddNewNotes.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION
                    );
                }else {
                    selectYourImage();
                }


            }
        });

        if (alreadyAvailableNote != null){
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
            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewNotes.this);
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

                            MyNoteDatabase.getMyNoteDatabase(getApplicationContext()).notesDao().deleteNote(alreadyAvailableNote);
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

    private void selectYourImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent,SELECT_IMG);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_PERMISSION && grantResults.length>0){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                selectYourImage();
            }else{
                Toast.makeText(this, "Sin Permisos!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== SELECT_IMG && resultCode == RESULT_OK){
            if(data != null){
                Uri selectImageUri = data.getData();
                if(selectImageUri != null){
                    try {
                        InputStream inputStream= getContentResolver().openInputStream(selectImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        addImg.setImageBitmap(bitmap);
                        addImg.setVisibility(View.VISIBLE);
                        SelectdImg= getPathFromUri(selectImageUri);
                    }catch (Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri uri){
        String filePath;
        Cursor cursor = getContentResolver()
                .query(uri,null,null,null,null);
        if(cursor == null){
            filePath = uri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }

        return filePath;
    }
}