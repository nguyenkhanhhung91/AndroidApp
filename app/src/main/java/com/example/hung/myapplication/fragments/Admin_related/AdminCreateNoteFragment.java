package com.example.hung.myapplication.fragments.Admin_related;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.activities.ViewModel;
import com.example.hung.myapplication.data.object_define.Note;
import com.example.hung.myapplication.data.object_define.TagAnalyzer;
import com.example.hung.myapplication.interfaces.AdminActivityInterface;

/**
 * Created by Hung on 11/16/2017.
 */

public class AdminCreateNoteFragment extends android.support.v4.app.Fragment {
    private TextView Title;
    private EditText mTitle;
    private EditText mTagString;
    private EditText mContent;
    private Button mCreate;
    private ViewModel mViewModel;
    private String title, tagString, content;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_admincreate, container, false);
        Title=(TextView) v.findViewById(R.id.admin_CreateNote);
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mTitle=(EditText) v.findViewById(R.id.CreateNote_Title);
        Title.setText(R.string.title_CreateNote);
        mTagString=(EditText) v.findViewById(R.id.CreateNote_TagString);
        mContent=(EditText) v.findViewById(R.id.CreateNote_Content);
        mCreate=(Button) v.findViewById(R.id.CreateNote_Create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean success=checkEditText();
                if(success) {
                    mViewModel.addNote(new Note(title, tagString, content));
                    ((AdminActivityInterface) getActivity()).GetInfoFromCreateNote(success);
                }
            }
        });
        return v;
    }
    private boolean checkEditText() {
        title =  mTitle.getText().toString();
        tagString =  mTagString.getText().toString();
        content =  mContent.getText().toString();
        boolean success=true;

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(title)) {
            mTitle.setError(getString(R.string.error_field_required));
            focusView = mTitle;
            cancel = true;
            success=false;
        }
        if (TextUtils.isEmpty(tagString)) {
            mTagString.setError(getString(R.string.error_field_required));
            focusView = mTagString;
            cancel = true;
            success=false;
        }
        else if (!isTagValid(tagString)) {
            mTagString.setError(getString(R.string.error_invalid_tagstring));
            focusView = mTagString;
            cancel = true;
            success=false;
        }
        if (TextUtils.isEmpty(content)) {
            mContent.setError(getString(R.string.error_field_required));
            focusView = mContent;
            cancel = true;
            success=false;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return success;
    }

    private boolean isTagValid(String tagString) {
        TagAnalyzer taganalyzer= new TagAnalyzer(tagString);
        return taganalyzer.IsvalidTag();
    }

}
