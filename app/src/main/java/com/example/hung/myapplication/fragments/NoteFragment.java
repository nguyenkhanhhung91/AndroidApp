package com.example.hung.myapplication.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.activities.ViewModel;
import com.example.hung.myapplication.data.object_define.Note;

/**
 * Created by Hung on 11/28/2017.
 */

public class NoteFragment extends android.support.v4.app.Fragment{
    private Note mNote;
    private TextView mNoteTitle, mNoteTagString, mNotecontent;
    private ViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        int i = getArguments().getInt("Note_ID");
        mNote=mViewModel.find(i);
        mNoteTitle=(TextView) v.findViewById(R.id.Note_title);
        mNoteTagString= (TextView) v.findViewById(R.id.Note_TagString);
        mNotecontent=(TextView)v.findViewById(R.id.Note_Content);

        mNoteTitle.setText("Title: " +mNote.getTitle());
        mNoteTagString.setText("Tag: "+mNote.getTagstring());
        mNotecontent.setText(mNote.getContent());
        return v;
    }
}
