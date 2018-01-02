package com.example.hung.myapplication.fragments.Admin_related;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.interfaces.AdminActivityInterface;

/**
 * Created by Hung on 11/16/2017.
 */

public class AdminInfoFragment extends android.support.v4.app.Fragment {
    private TextView mtitle;
    private Button mCreateNote;
    private Button mNotelist;
    private Button mLogout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_admininfo, container, false);
        mtitle=(TextView) v.findViewById(R.id.admin_info_title);
        mtitle.setText(R.string.title_Admininfo);
        mCreateNote=(Button) v.findViewById(R.id.admin_info_CreatNote);
        mNotelist=(Button) v.findViewById(R.id.admin_info_NoteList);
        mLogout=(Button) v.findViewById(R.id.admin_info_toLogOut);
        mCreateNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AdminActivityInterface)getActivity()).GetInfoFromInfo(true, false,false);
            }
        });
        mNotelist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AdminActivityInterface)getActivity()).GetInfoFromInfo(false, true,false);
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AdminActivityInterface)getActivity()).GetInfoFromInfo(false, false,true);
            }
        });
        return v;
    }
}
