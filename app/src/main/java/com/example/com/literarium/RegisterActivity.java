package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private EditText grUsername;
    private EditText lUsername;
    private EditText lPassword;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        grUsername = findViewById(R.id.grusername);
        lUsername = findViewById(R.id.lusername);
        lPassword = findViewById(R.id.lpassword);

    }

    protected void register(View v){

        // check if edit texts are not empty
        if(TextUtils.isEmpty(grUsername.getText()) || TextUtils.isEmpty(lUsername.getText()) || TextUtils.isEmpty(lPassword.getText())){ // one is empty
            Toast.makeText(this, "Missing credential(s), please enter value(s)!", Toast.LENGTH_SHORT).show();
        }

        // query

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }

}
