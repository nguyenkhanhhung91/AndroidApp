package com.example.hung.myapplication.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.data.object_define.User;
import com.example.hung.myapplication.fragments.Login_related.MainLoginFragment;
import com.example.hung.myapplication.fragments.Login_related.MainSignUpFragment;
import com.example.hung.myapplication.interfaces.MainActivityInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {
    private List<User> mUsers;

    private FragmentManager fm;
    private MainLoginFragment mLoginFragment;
    private MainSignUpFragment mSignUpFragment;
    private ViewModel mViewModel;
    public static final int FRAGMENT_LOGIN = 0;
    public static final int FRAGMENT_SIGNUP = 1;
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mUsers=mViewModel.getUsers();

        fm = getSupportFragmentManager();
        mLoginFragment = new MainLoginFragment();
        mSignUpFragment = new MainSignUpFragment();

        if(savedInstanceState == null){
            fm.beginTransaction().add(R.id.fragment_container, mLoginFragment).commit();
            currentFragment = FRAGMENT_LOGIN;
        }else{
            currentFragment = savedInstanceState.getInt("currentFragment");
            switch(currentFragment){
                case FRAGMENT_LOGIN:
                    AddFragmentLogin();
                    break;
                case FRAGMENT_SIGNUP:
                    AddFragmentSignup();
                    break;
            }
        }
    }

    @Override
    public void GetInfoFromSignIn(boolean toSignUp, String email, String password) {
        if(toSignUp)
            AddFragmentSignup();
        else{
            if(email.equals("admin@gmail.com")) {
                if ("admin".equals(password)) {         //case log in as admin
                    Toast.makeText(this,"Welcome Admin!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
            else{for (User credential : mUsers) {               //check password
                if (credential.getEmail().equals(email)) {
                    if (credential.getPassword().equals(password)) {
                        Intent intent = new Intent(this, UserActivity.class);
                        intent.putExtra("USER_EMAIL", credential.getEmail());
                        startActivity(intent);
                        Toast.makeText(this,"Password is Correct.Please wait",Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
            }
            Toast.makeText(this,"Username and password combination is incorrect",Toast.LENGTH_SHORT).show();}
        }
    }

    @Override
    public void GetInfoFromSignUp(boolean toSignIn, String email, String password, boolean success) {
        if(toSignIn)
            AddFragmentLogin();
        else {
            if(success) {
                mViewModel.addUser(new User(email, password, false));//new user will not be admin
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
                mUsers=mViewModel.getUsers();
                return;
            }
            Toast.makeText(this,"Try again",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("currentFragment", currentFragment);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void AddFragmentLogin(){
        fm.beginTransaction().replace(R.id.fragment_container, mLoginFragment).commit();
        currentFragment = FRAGMENT_LOGIN;
    }
    public void AddFragmentSignup(){
        fm.beginTransaction().replace(R.id.fragment_container, mSignUpFragment).commit();
        currentFragment = FRAGMENT_SIGNUP;
    }
}
