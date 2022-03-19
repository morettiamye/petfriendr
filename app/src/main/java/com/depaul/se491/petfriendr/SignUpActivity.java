/*
        User signup page.  User will provide:
        -username
        -email address
        -password
        - check if username exists, if so error and reset.  if username is new, entries are added to the database.
        user is then authenticated and taken to the Edit Profile screen.
 */

package com.depaul.se491.petfriendr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    Button createAccount;
    EditText etUserEmail, etUserPassword, etUserName;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUserEmail = findViewById(R.id.inputEmail);
        etUserPassword = findViewById(R.id.inputPassword);
        createAccount = findViewById(R.id.createAccountButton);
        etUserName = findViewById(R.id.username);
        mAuth  = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFirebaseAuthentication();
            }
        });
    }

    private void performFirebaseAuthentication() {
        String emailString = etUserEmail.getText().toString();
        String passwordString = etUserPassword.getText().toString();
        String userName = etUserName.getText().toString();
        if (userName.isEmpty()){
            etUserName.setError("Username cannot be blank");
        }
        else if(!emailString.matches(emailPattern)){
            etUserEmail.setError("Enter Valid Email Address");
        }else if(passwordString.isEmpty() || passwordString.length()<6){
            etUserPassword.setError("Password cannot be empty or less than 6 characters in length");
        }else{
            mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendUserToNextActivity();
                        Toast.makeText(SignUpActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignUpActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignUpActivity.this, EditProfileFragment.class);
        startActivity(intent);
    }

}
