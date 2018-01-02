package com.example.hung.myapplication.fragments.User_related;

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
import com.example.hung.myapplication.data.object_define.TagAnalyzer;
import com.example.hung.myapplication.data.object_define.User;
import com.example.hung.myapplication.interfaces.UserActivityInterface;

/**
 * Created by Hung on 11/16/2017.
 */

public class UserSubscribeFragment extends android.support.v4.app.Fragment{
    private TextView mExample, mtitle;
    private EditText mTag;
    private Button mSubscribe;
    private String tag;
    private ViewModel mViewModel;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_subscribe_to, container, false);
        mExample=(TextView) v.findViewById(R.id.subscribe_to_example);
        mTag=(EditText) v.findViewById(R.id.subscribe_to_tag);
        String user_email = getArguments().getString("USER_EMAIL");
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        user=mViewModel.getCurrentuser(user_email);
        mtitle=(TextView) v.findViewById(R.id.subscribe_to_title);
        mtitle.setText(R.string.title_Usersubscribe);
        mSubscribe=(Button) v.findViewById(R.id.subscribe_to_subscribe);
        mSubscribe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean success=checkEditText();
                if(success) {
                    mViewModel.addTag(tag,user);
                    ((UserActivityInterface) getActivity()).GetInfoFromSubscribe(success);
                }
            }
        });
        return v;
    }
    private boolean checkEditText() {
        tag =  mTag.getText().toString();
        boolean success=true;

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(tag)) {
            mTag.setError(getString(R.string.error_field_required));
            focusView = mTag;
            cancel = true;
            success=false;
        }
        else if (!isTagValid(tag)) {
            mTag.setError(getString(R.string.error_invalid_tagstring));
            focusView = mTag;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}