package com.example.hung.myapplication.fragments.Login_related;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.interfaces.MainActivityInterface;

/**
 * Created by Hung on 11/16/2017.
 */

public class MainLoginFragment extends android.support.v4.app.Fragment   {
    private EditText mEmail, mPassword;
    private Button mSignIn, mSignUp;
    private TextView mStatus, mtitle;
    private String email, password;
    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mEmail=(EditText) v.findViewById(R.id.login_email);
        mPassword=(EditText) v.findViewById(R.id.login_password);
        mtitle=(TextView) v.findViewById(R.id.login_title);
        mtitle.setText(R.string.title_Signin);
        mStatus=(TextView) v.findViewById(R.id.login_status);
        mStatus.setText(R.string.status_sign_in);
        mSignIn=(Button) v.findViewById(R.id.login_signin);
        mSignUp=(Button) v.findViewById(R.id.login_signup);
        mSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkEditText();
                ((MainActivityInterface)getActivity()).GetInfoFromSignIn(false, email, password);
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivityInterface)getActivity()).GetInfoFromSignIn(true, null, null);
            }
        });
        return v;
    }
    private void checkEditText() {
        email =  mEmail.getText().toString();
        password =  mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }
}
