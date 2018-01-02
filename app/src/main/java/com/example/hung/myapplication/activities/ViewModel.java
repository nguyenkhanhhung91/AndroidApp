package com.example.hung.myapplication.activities;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.persistence.room.Room;

import com.example.hung.myapplication.data.AppDatabase;
import com.example.hung.myapplication.data.object_define.Note;
import com.example.hung.myapplication.data.object_define.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hung on 11/26/2017.
 */

public class ViewModel extends AndroidViewModel {
    private List<User> mUsers;
    private List<Note> mNotes;
    private List<Note> mNotesforUser;
    private AppDatabase appDatabase;

    public ViewModel(Application application){
        super(application);
        appDatabase= Room.databaseBuilder(application, AppDatabase.class, "users")
                .allowMainThreadQueries()
                .build();
        appDatabase.userDao().insertUser(new User("admin@gmail.com","admin", true));//default admin account
        mUsers=appDatabase.userDao().getAll();
        mNotes=appDatabase.noteDao().getAll();
    }
    public List<User> getUsers() {
        return mUsers;
    }

    public List<Note> getNotesforAdmin() {
        return mNotes;
    }

    public List<Note> getNotesforUser(String email) {                 //send a list note match to user subscribe
        mNotesforUser = new ArrayList<>();
        User user=getCurrentuser(email);
        String stringtag=user.getTagstring();
        String[] tags = stringtag.split(",");
        for (int i=0; i<tags.length;i++) {                              //simple search algorithm for now
            for (int j=0; j<mNotes.size();j++) {
                if (tags[i].equals(mNotes.get(j).getTagstring()))
                    mNotesforUser.add(mNotes.get(j));
            }
        }
        return mNotesforUser;
    }

    public Note find(int id) {                      //find a note with id
        for (Note note : mNotes) {
            if (note.getNoteid()==id)
                return note;
        }
        return null;
    }

    public User getCurrentuser(String email) {      //find a user with email
        for (User credential : mUsers) {
            if (credential.getEmail().equals(email))
                return credential;
        }
        return null;
    }

    public void addUser(User user){                 //add a new user to database
        appDatabase.userDao().insertUser(user);
        mUsers=appDatabase.userDao().getAll();
   }

    public void addNote(Note note){                 //add a new note to database
        appDatabase.noteDao().insertNote(note);
        mNotes=appDatabase.noteDao().getAll();
    }

    public void addTag(String string, User user){      //update TagString for user
        char first = string.charAt(0);
        char second = string.charAt(1);
        if (first == 'l' && second == '-')     {         //this one for location update
            user.setTagstring(string, "replace");
        } else
            user.setTagstring(string,"add");
        addUser(user);
    }
}