package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity {
    EditText edtLogInEmail;
    EditText edtLogInPassword;
    Button btnLogInToHome;
    Button btnBackToSignUpActivity;
    ConstraintLayout logInLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("Log In");
        edtLogInEmail = (EditText) findViewById(R.id.edtLogInEmail);
        edtLogInPassword = (EditText) findViewById(R.id.edtLogInPassword);
        btnLogInToHome = (Button) findViewById(R.id.btnLoginToHome);
        btnBackToSignUpActivity = (Button) findViewById(R.id.btnBackToSignUp);
        logInLayout=(ConstraintLayout)findViewById(R.id.logInLayout);
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.logOut();
        }
        btnLogInToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
        btnBackToSignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edtLogInPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((keyCode==KeyEvent.KEYCODE_ENTER)&&(event.getAction()==KeyEvent.ACTION_DOWN)){

                }
                return false;
            }
        });
        logInLayout.setOnClickListener(new View.OnClickListener() {
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
    void logIn() {
        String email = edtLogInEmail.getText().toString();
        String password = edtLogInPassword.getText().toString();
        ParseUser User = new ParseUser();
        User.setEmail(email);
        User.setPassword(password);
        if ((email.isEmpty() == false) &&
                (password.isEmpty() == false)) {
            ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
            progressDialog.setTitle("Logging In...");
            progressDialog.setMessage("Wait Until Logging in is Finished");
            progressDialog.show();
            User.logInInBackground(email, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    progressDialog.dismiss();//stop progress dialog
                    if (user != null && e == null) {//The user is logged in.
                        FancyToast.makeText(LogInActivity.this, ParseUser.getCurrentUser().getUsername() + "is Logged in Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        transitionToSocialMediaActivity();
                    } else {// Signup failed. Look at the ParseException to see what happened.
                        FancyToast.makeText(LogInActivity.this, "failed to log in \n" + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                    }
                }
            });

        } else {
            FancyToast.makeText(LogInActivity.this, "failed to sign in \n" + "Email, Password are required fields ", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }
    void transitionToSocialMediaActivity(){
        Intent gotToSocialMediaActivity = new Intent(LogInActivity.this, SocialMediaActivity.class);
        startActivity(gotToSocialMediaActivity);
    }
}