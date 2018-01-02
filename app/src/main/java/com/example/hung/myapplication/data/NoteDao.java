package com.example.hung.myapplication.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hung.myapplication.data.object_define.Note;

import java.util.List;

/**
 * Created by Hung on 11/25/2017.
 */
@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT * FROM Note WHERE Noteid=:mid")
    Note find(int mid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Update
    public void updateNotes(Note... notes);
}
