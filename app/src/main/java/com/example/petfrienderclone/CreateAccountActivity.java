package com.example.petfrienderclone;

import static com.example.petfrienderclone.R.id.createaccount;
import static com.example.petfrienderclone.R.id.email;
import static com.example.petfrienderclone.R.id.password;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    Button createAccountButton;
    EditText etUserEmail, etUserPassword;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUserEmail = findViewById(R.id.email);
        etUserPassword= findViewById(R.id.password);
        createAccountButton = findViewById(createaccount);

        mAuth  = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoginInfo();
            }
        });



//        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override //everything the status changes, something happens that triggers this function
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                //will have info on current logged in user
//                //if use not logged in, will be null
//                if (user !=null){
//                    //user is logged in and can move onto the next activity
//                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//                //if use null, user can choose if he/she wants toregister or not .
//
//            }
//        };

    }










    private void getLoginInfo() {
        //final String userName = etUserName.getText().toString();
        String userEmail = etUserEmail.getText().toString();
        String userPassword = etUserPassword.getText().toString();

        if (!userEmail.matches(emailPattern)){
            etUserEmail.setError("Enter correct email");

        }else if (userPassword.isEmpty() || userPassword.length()<6){
            etUserPassword.setError("Enter proper password");
        }else{
            //register the user
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendUserToSwiping();
                        Toast.makeText(CreateAccountActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CreateAccountActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();


                    }

                }
            });
        }

    }

    private void sendUserToSwiping() {
        Intent intent = new Intent(CreateAccountActivity.this, SwipingActivity.class);
        startActivity(intent);

    }

}