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

public class SignUpActivity extends BaseActivity {

    Button createAccount;
    EditText etUserEmail, etUserPassword, etUserName;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_signup);
        super.onCreate(savedInstanceState);

        etUserEmail = findViewById(R.id.inputEmail);
        etUserPassword = findViewById(R.id.inputPassword);
        createAccount = findViewById(R.id.createAccountButton);
        //etUserName = findViewById(R.id.createUsername_textField);
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
       // String nameString = etUserName.getText().toString();
        if (!emailString.matches(emailPattern)){
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
        //TODO: Update DisplayProfileActivity.class to the appropriate class after the user successfully  creates and account.
        Intent intent = new Intent(SignUpActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

}


