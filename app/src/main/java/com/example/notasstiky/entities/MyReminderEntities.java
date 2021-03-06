package com.example.notasstiky.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName= "reminder")
public class MyReminderEntities implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "date_time")
    private String dateTime;
    @ColumnInfo(name="note_text")
    private String noteText;
    @ColumnInfo(name = "date_time_complete")
    private String dateTimeComplete;
    @ColumnInfo(name = "terminado")
    private boolean terminado;

    @ColumnInfo(name = "color")
    private String color;


    public String getDateTimeComplete() {
        return dateTimeComplete;
    }

    public void setDateTimeComplete(String dateTimeComplete) {
        this.dateTimeComplete = dateTimeComplete;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
