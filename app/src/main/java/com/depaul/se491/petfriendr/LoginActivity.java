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

        etUserEmail = findViewById(R.id.usernameSignIn_textfield);
        etUserPassword= findViewById(R.id.passwordSignUp_textField);
        loginButton = findViewById(login);

        mAuth  = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                getLoginInfo();
            }
        });



    }

    private void getLoginInfo() {
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
                        sendUserToSwiping();
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();


                    }

                }
            });
        }

    }

   private void sendUserToSwiping() {
        Intent intent = new Intent(LoginActivity.this, SeePetsActivity.class);
        startActivity(intent);

    }

}

    /*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Listener part - checks change in the login status so that if logged in can move onto main activity
        firebaseAuth  = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override //everything the status changes, something happens that triggers this function
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //will have info on current logged in user
                //if use not logged in, will be null
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user !=null){
                    //user is logged in and can move onto the next activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                //if use null, user can choose if he/she wants toregister or not .
            }
        };







    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);

    }*/

