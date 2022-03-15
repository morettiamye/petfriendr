/*
        This will allow the user to log in.
        - Username and password field, checks database and logs in if valid
        - Button to go to Signup page
 */

package com.depaul.se491.petfriendr;

import static com.depaul.se491.petfriendr.R.id.login;

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

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText etUserEmail, etUserPassword;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    //FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserEmail = findViewById(R.id.loginEmail);
        etUserPassword= findViewById(R.id.loginPassword);
        loginButton = findViewById(login);

        mAuth  = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserEmailAndPassword();
            }
        });



    }

    private void loginUserEmailAndPassword() {
        String userEmail = etUserEmail.getText().toString();
        String userPassword = etUserPassword.getText().toString();

        if (!userEmail.matches(emailPattern)){
            etUserEmail.setError("Enter correct email");

        }else if (userPassword.isEmpty() || userPassword.length()<6){
            etUserPassword.setError("Enter proper password");
        }else{
            //login user
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendUserToNextActivity();

                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();


                    }

                }
            });
        }

    }

    private void sendUserToNextActivity() {
        //TODO: Update DisplayProfileActivity.class to the appropriate class after the user successfully  creates and account.
        Intent intent = new Intent(LoginActivity.this, SeePetsActivity.class);
        startActivity(intent);

    }

}


