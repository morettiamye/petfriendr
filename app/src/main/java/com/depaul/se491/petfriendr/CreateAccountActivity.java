package com.depaul.se491.petfriendr;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    // Resource ID
    // TODO: Replace resource values
    private final static int LAYOUT_CREATE_ACCOUNT = 0;
    private final static int EDIT_TEXT_EMAIL = 0;
    private final static int EDIT_TEXT_USERNAME = 0;
    private final static int EDIT_TEXT_PASSWORD = 0;
    private final static int BUTTON_SUBMIT = 0;
    private final static int STRING_INFO_INVALID = 0;
    private final static int STRING_OK = 0;

    // Text View
    private TextView textEmail;
    private TextView textUsername;
    private TextView textPassword;

    // Button View
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_CREATE_ACCOUNT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find view by ID
        textEmail = findViewById(EDIT_TEXT_EMAIL);
        textUsername = findViewById(EDIT_TEXT_USERNAME);
        textPassword = findViewById(EDIT_TEXT_PASSWORD);
        buttonSubmit = findViewById(BUTTON_SUBMIT);
        assert textEmail != null;
        assert textUsername != null;
        assert textPassword != null;
        assert buttonSubmit != null;

        // Add button click listener
        buttonSubmit.setOnClickListener(view -> onButtonClickSubmit());
    }

    // Called when submit button in clicked
    private void onButtonClickSubmit() {
        if (isInfoValid()) {
            storeUserInfo();
        }
        else {
            showDialogInfoInvalid();
        }

        // Launch next activity
        // TODO: Replace with correct activity to launch
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Validates user information
    private boolean isInfoValid() {
        // TODO: Check if user information is valid
        return false;
    }

    private void storeUserInfo() {
        // TODO: Store user account information
    }

    private void showDialogInfoInvalid() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(STRING_INFO_INVALID);
        builder.setPositiveButton(STRING_OK, (dialog, which) -> { });
        builder.show();
    }
}