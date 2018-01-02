package com.example.hung.myapplication.data.object_define;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Hung on 10/20/2017.
 */
@Entity(tableName = "Note")
public class Note  {
    @PrimaryKey(autoGenerate = true)
    private int Noteid;

    public void setNoteid(int noteid) {
        Noteid = noteid;
    }

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "tagstring")
    private String tagstring;

    public String getTagstring() {
        return tagstring;
    }

    public void setTagstring(String tagstring) {
        this.tagstring = tagstring;
    }

    public int getNoteid() {
        return Noteid;
    }

    public Note(String title, String tagstring, String content) {
        this.title = title;
        this.content = content;
        this.tagstring = tagstring;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
