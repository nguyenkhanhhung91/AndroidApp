package com.example.hung.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.fragments.Admin_related.AdminCreateNoteFragment;
import com.example.hung.myapplication.fragments.Admin_related.AdminInfoFragment;
import com.example.hung.myapplication.fragments.NoteFragment;
import com.example.hung.myapplication.fragments.NoteListFragment;
import com.example.hung.myapplication.interfaces.AdminActivityInterface;
import com.example.hung.myapplication.interfaces.ListInterface;

public class AdminActivity extends AppCompatActivity implements AdminActivityInterface, ListInterface {
    private FragmentManager fm;
    private AdminCreateNoteFragment mAdminCreateNoteFragment;
    private AdminInfoFragment mAdminInfoFragment;
    private NoteListFragment mNoteListFragment;
    public static final int FRAGMENT_INFO = 0;
    public static final int FRAGMENT_CREATE = 1;
    public static final int FRAGMENT_LIST = 2;
    public static final int FRAGMENT_NOTE = 3;
    private int currentFragment;
    private NoteFragment mNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        fm = getSupportFragmentManager();
        mAdminCreateNoteFragment = new AdminCreateNoteFragment();
        mAdminInfoFragment= new AdminInfoFragment();
        mNoteListFragment = new NoteListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ACTIVITY", "admin");           //identify Activity is AdminActivity
        mNoteListFragment.setArguments(bundle);         //send to List Fragment to create list accordingly to activity
        mNoteFragment=new NoteFragment();
        if(savedInstanceState == null){
            fm.beginTransaction().add(R.id.fragment_container, mAdminInfoFragment).commit();
            currentFragment = FRAGMENT_INFO;
        }else{
            currentFragment = savedInstanceState.getInt("currentFragment");
            switch(currentFragment){
                case FRAGMENT_INFO:
                    fm.beginTransaction().replace(R.id.fragment_container, mAdminInfoFragment).commit();
                    break;
                case FRAGMENT_CREATE:
                    fm.beginTransaction().replace(R.id.fragment_container, mAdminCreateNoteFragment).commit();
                    break;
                case FRAGMENT_LIST:
                    fm.beginTransaction().replace(R.id.fragment_container, mNoteListFragment).commit();
                    break;
                case FRAGMENT_NOTE:
                    fm.beginTransaction().replace(R.id.fragment_container, mNoteFragment).commit();
                    break;
            }
        }
    }

    @Override
    public void GetInfoFromInfo(boolean ToCreate, boolean TonoteList, boolean ToLogout) {
        if(ToCreate) {
            currentFragment = FRAGMENT_CREATE;
            fm.beginTransaction().replace(R.id.fragment_container, mAdminCreateNoteFragment).commit();
        }
        if(ToLogout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(TonoteList) {
            currentFragment = FRAGMENT_LIST;
            fm.beginTransaction().replace(R.id.fragment_container, mNoteListFragment).commit();
        }
    }

    @Override
    public void GetInfoFromCreateNote(boolean success) {
        if(success) {
            Toast.makeText(this, "Note is sent", Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.fragment_container, mAdminInfoFragment).commit();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("currentFragment", currentFragment);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void onBackPressed(){
        if(currentFragment==FRAGMENT_INFO)
            finish();
        else if(currentFragment==FRAGMENT_NOTE) {
            currentFragment=FRAGMENT_LIST;
            fm.beginTransaction().replace(R.id.fragment_container, mNoteListFragment).commit();
        }
        else if(currentFragment==FRAGMENT_LIST||currentFragment==FRAGMENT_CREATE) {
            currentFragment=FRAGMENT_INFO;
            fm.beginTransaction().replace(R.id.fragment_container, mAdminInfoFragment).commit();
        }
    }

    @Override
    public void GetInfoFromList(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("Note_ID", id);
        mNoteFragment.setArguments(bundle);
        currentFragment=FRAGMENT_NOTE;
        fm.beginTransaction().replace(R.id.fragment_container, mNoteFragment).commit();
    }
}
