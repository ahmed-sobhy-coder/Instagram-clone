package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    EditText edtUserName;
    Button btnSignUp;
    Button btnLogIn;
    ConstraintLayout signUpLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        edtUserName = (EditText) findViewById(R.id.edtSignUpUserName);
        edtEmail = (EditText) findViewById(R.id.edtSignUpEmail);
        edtPassword = (EditText) findViewById(R.id.edtSignUpPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnLogIn = (Button) findViewById(R.id.btnLogin);
        signUpLayout=(ConstraintLayout)findViewById(R.id.signUpLayout);
        if (ParseUser.getCurrentUser() != null) {
            transitionToSocialMediaActivity();
        }
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotToLogInActivity = new Intent(SignUpActivity.this, LogInActivity.class);
                transitionToLogInActivity();
            }
        });
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent KeyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && KeyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    signUp();
                }
                return false;
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        signUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager keyboard= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                /*
                * public abstract View getCurrentFocus ()
                * Return the view in this Window that currently has focus, or null if there are none. Note that this does not look in any containing Window.
                * getWindowToken(). Retrieve a unique token identifying the window this view is attached to
                * */
                try { //if keyboard is shown you can hide it when u press to layout, but
                    // if u press into layout with no keyboard is shown it will rise an exception
                    keyboard.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0); //hide keyboard when the user presses into layout
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    void signUp() {
        String name = edtUserName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        ParseUser User = new ParseUser();
        User.setUsername(name);
        User.setEmail(email);
        User.setPassword(password);
        if ((email.isEmpty() == false) &&
                (password.isEmpty() == false) &&
                (name.isEmpty() == false)) {
            ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setTitle("Signing Up...");
            progressDialog.setMessage("Wait Until Signing Up is Finished");
            progressDialog.show();
            User.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    progressDialog.dismiss();//stop progress dialog
                    if (e == null) {//user is signed up successfully
                        FancyToast.makeText(SignUpActivity.this, "Signed Up Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        transitionToSocialMediaActivity();
                    } else {
                        FancyToast.makeText(SignUpActivity.this, "failed to sign up \n" + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                    }
                }
            });
        } else {
            FancyToast.makeText(SignUpActivity.this, "failed to sign up \n" + "Username, Email, Password are required fields ", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

        }
    }
    void transitionToLogInActivity(){
        Intent gotToLogInActivity = new Intent(SignUpActivity.this, LogInActivity.class);
      //  gotToLogInActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gotToLogInActivity);
    }
    void transitionToSocialMediaActivity(){
        Intent gotToSocialMediaActivity = new Intent(SignUpActivity.this, SocialMediaActivity.class);
        startActivity(gotToSocialMediaActivity);
    }
}
