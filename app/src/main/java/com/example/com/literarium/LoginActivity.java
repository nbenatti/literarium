package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;

import javax.xml.xpath.XPathExpressionException;

public class LoginActivity extends Activity {

    private Button loginButton;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //getActionBar().hide();

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetUserInput();
    }

    public void login(View v) {

        LoginTask loginTask = new LoginTask(this, userName.getText().toString().trim(), password.getText().toString().trim());
        loginTask.execute();
    }

    private void resetUserInput() {
        userName.setText("");
        password.setText("");
    }

    /**
     * redirects the to the mainActivity
     */
    public void handleLoginSuccess() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * incorrect credentials
     */
    public void handleLoginFailure() {

        Toast.makeText(this, "wrong credentials!", Toast.LENGTH_SHORT).show();
        resetUserInput();
    }
}
