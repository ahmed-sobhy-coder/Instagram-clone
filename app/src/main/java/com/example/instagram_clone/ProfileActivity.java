package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {
    Button btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnLogOut=(Button)findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                if(ParseUser.getCurrentUser()==null){
                    finish();
                }
            }
        });
    }
}