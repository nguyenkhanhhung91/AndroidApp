package com.example.hung.myapplication.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.hung.myapplication.data.object_define.Note;
import com.example.hung.myapplication.data.object_define.User;

/**
 * Created by Hung on 11/24/2017.
 */

@Database(entities = {User.class, Note.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract NoteDao noteDao();
}