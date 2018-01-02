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
 * Created by Hung on 11/19/2017.
 */

public class MainSignUpFragment extends android.support.v4.app.Fragment {
    private EditText mEmail, mPassword;
    private Button mSignIn, mSignUp;
    private TextView mStatus, mtitle;
    private String email, password;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mEmail=(EditText) v.findViewById(R.id.login_email);
        mPassword=(EditText) v.findViewById(R.id.login_password);
        mStatus=(TextView) v.findViewById(R.id.login_status);
        mtitle=(TextView) v.findViewById(R.id.login_title);
        mtitle.setText(R.string.title_Signup);
        mStatus.setText(R.string.status_sign_up);
        mSignIn=(Button) v.findViewById(R.id.login_signin);
        mSignUp=(Button) v.findViewById(R.id.login_signup);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean success=checkEditText();
                ((MainActivityInterface)getActivity()).GetInfoFromSignUp(false, email, password, success);
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivityInterface)getActivity()).GetInfoFromSignUp(true, null, null, false);
            }
        });
        return v;
    }
    private boolean checkEditText() {
        email =  mEmail.getText().toString();
        password =  mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        boolean success=true;

        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
            success=false;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
            success=false;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
            success=false;
        } else if (!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
            success=false;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return success;
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }
}
