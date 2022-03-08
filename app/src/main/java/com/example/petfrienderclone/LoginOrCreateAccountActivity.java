package com.example.petfrienderclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOrCreateAccountActivity extends AppCompatActivity {

    private Button loginButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        loginButton = (Button) findViewById(R.id.login);
        createAccountButton = (Button) findViewById(R.id.createaccount);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginOrCreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
                }
            }
        );

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginOrCreateAccountActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


    }
}