package com.example.hung.myapplication.data.object_define;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Hung on 11/24/2017.
 */

@Entity(tableName = "User")
public class User {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "tagstring")     //location is the first substring
    private String tagstring;

    public String getTagstring() {
        return tagstring;
    }

    public void setTagstring(String tagstring) {
        this.tagstring = tagstring;
    }

    public void setTagstring(String tagstring, String method) { //update TagString
        String[] tags;
        if(this.tagstring.equals(""))                       //case subscribe to nothing yet
            this.tagstring = tagstring;
        else if(method.equals("add"))                       //case add to current string
            this.tagstring = this.tagstring+tagstring;
        else if(method.equals("replace")) {                 //case replace location
            tags = this.tagstring.split(",");
            tags[0] = tagstring;
            String s="";
            for (int i=0;i<tags.length;i++){
                    s+=tags[i]+",";
            }
            this.tagstring=s;
        }
    }

    @ColumnInfo(name = "isadmin")
    private boolean isadmin;

    public User( String email, String password, boolean isadmin) {
        this.email = email;
        this.password = password;
        this.isadmin=isadmin;
        this.tagstring="";
    }

    public boolean isIsadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}