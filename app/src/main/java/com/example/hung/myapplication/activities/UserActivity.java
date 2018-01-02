package com.example.hung.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.fragments.NoteFragment;
import com.example.hung.myapplication.fragments.NoteListFragment;
import com.example.hung.myapplication.fragments.User_related.UserInfoFragment;
import com.example.hung.myapplication.fragments.User_related.UserSubscribeFragment;
import com.example.hung.myapplication.interfaces.ListInterface;
import com.example.hung.myapplication.interfaces.UserActivityInterface;

public class UserActivity extends AppCompatActivity implements UserActivityInterface,ListInterface {
    private FragmentManager fm;
    private UserInfoFragment mUserInfoFragment;
    private NoteListFragment mNoteListFragment;
    private UserSubscribeFragment mUserSubscribeFragment;
    public static final int FRAGMENT_INFO = 0;
    public static final int FRAGMENT_LIST = 1;
    public static final int FRAGMENT_SUBSCRIBE = 2;
    public static final int FRAGMENT_NOTE = 3;
    private NoteFragment mNoteFragment;
    private int currentFragment;
    private String user_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        user_email = getIntent().getStringExtra("USER_EMAIL");
        fm = getSupportFragmentManager();

        mUserInfoFragment = new UserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("USER_EMAIL", user_email);     //send user email to fragments
        mUserInfoFragment.setArguments(bundle);

        mNoteListFragment = new NoteListFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("ACTIVITY", "user");          //identify Activity is UserActivity
        bundle2.putString("USER_EMAIL", user_email);
        mNoteListFragment.setArguments(bundle2);        //send to List Fragment to create list accordingly to activity

        mNoteFragment=new NoteFragment();
        mUserSubscribeFragment= new UserSubscribeFragment();
        mUserSubscribeFragment.setArguments(bundle);

        if(savedInstanceState == null){
            fm.beginTransaction().add(R.id.fragment_container, mUserInfoFragment).commit();
            currentFragment = FRAGMENT_INFO;
        }else{
            currentFragment = savedInstanceState.getInt("currentFragment");
            switch(currentFragment){
                case FRAGMENT_INFO:
                    fm.beginTransaction().replace(R.id.fragment_container, mUserInfoFragment).commit();
                    break;
                case FRAGMENT_LIST:
                    fm.beginTransaction().replace(R.id.fragment_container, mNoteListFragment).commit();
                    break;
                case FRAGMENT_SUBSCRIBE:
                    fm.beginTransaction().replace(R.id.fragment_container, mUserSubscribeFragment).commit();
                    break;
                case FRAGMENT_NOTE:
                    fm.beginTransaction().replace(R.id.fragment_container, mNoteFragment).commit();
                    break;
            }
        }
    }

    @Override
    public void GetInfoFromInfo(boolean Tosubscribe, boolean TonoteList, boolean ToLogout) {
        if(Tosubscribe) {
            currentFragment = FRAGMENT_SUBSCRIBE;
            fm.beginTransaction().replace(R.id.fragment_container, mUserSubscribeFragment).commit();
        }
        if(TonoteList) {
            currentFragment = FRAGMENT_LIST;
            fm.beginTransaction().replace(R.id.fragment_container, mNoteListFragment).commit();
        }
        if(ToLogout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void GetInfoFromSubscribe(boolean success) {
        if(success) {
            Toast.makeText(this, "Subscribed!!", Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.fragment_container, mUserInfoFragment).commit();
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
        else if(currentFragment==FRAGMENT_LIST||currentFragment==FRAGMENT_SUBSCRIBE){
            currentFragment=FRAGMENT_INFO;
            fm.beginTransaction().replace(R.id.fragment_container, mUserInfoFragment).commit();
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
