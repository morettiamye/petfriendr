/*
    This screen populates the selected profile to display:
        - Name
        - Profile Picture
        - Profile Description
        - Messages left
        - Leave message
 */

package com.depaul.se491.petfriendr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.depaul.se491.petfriendr.models.Comment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DisplayProfileActivity extends BaseActivity {

    private DatabaseReference mDatabase;
    Button submitComment;
    EditText newCommentText;
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_display_profile);
        super.onCreate(savedInstanceState);
        String userName = getIntent().getStringExtra("User Name");
        String petName = getIntent().getStringExtra("Pet Name");
        String imageUrl = getIntent().getStringExtra("Image URL");
        String message = getIntent().getStringExtra("Message");
        TextView textUser = findViewById(R.id.display_text_user_name);
        TextView textPet = findViewById(R.id.display_text_pet_name);
        TextView textMessage = findViewById((R.id.display_text_message));
        ImageView imagePic = findViewById(R.id.display_image_profile_pic);
        textUser.setText(userName);
        textPet.setText(petName);
        textMessage.setText(message);
        Picasso.get().load(imageUrl).into(imagePic);
        newCommentText = findViewById(R.id.editTextTextPersonName);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        submitComment = findViewById(R.id.submitComment);
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewComment(userName);
            }
        });
    }

    private void addNewComment(String userName){
        String newComment = newCommentText.getText().toString();

        mAuth  = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Comment newCommentData = new Comment(mUser,newComment,userName);
        mDatabase.child("comments").child(newCommentData.getId()).setValue(newCommentData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });
    }
}