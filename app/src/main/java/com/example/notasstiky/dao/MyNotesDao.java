package com.example.notasstiky.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.notasstiky.entities.MyNoteEntities;
import com.example.notasstiky.entities.MyReminderEntities;

import java.util.List;

@Dao
public interface MyNotesDao {
    //////////////////////////////////////////////Notes
    @Query("SELECT * FROM note ORDER BY id DESC")
    List<MyNoteEntities> getAllNotes();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertNote(MyNoteEntities noteEntities);
    @Delete
    void deleteNote(MyNoteEntities noteEntities);
/////////////////////////////////////////////////Reminder
@Query("SELECT * FROM reminder ORDER BY id DESC")
List<MyReminderEntities> getAllReminder();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertReminder(MyReminderEntities reminderEntities);
    @Delete
    void deleteReminder(MyReminderEntities reminderEntities);

}
