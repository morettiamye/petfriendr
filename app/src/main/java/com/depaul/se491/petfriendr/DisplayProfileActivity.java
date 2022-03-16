/*
    This screen populates the selected profile to display:
        - Name
        - Profile Picture
        - Profile Description
        - Messages left
        - Leave message
 */

package com.depaul.se491.petfriendr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_display_profile);
        super.onCreate(savedInstanceState);
        String userName = getIntent().getStringExtra("User Name");
        String petName = getIntent().getStringExtra("Pet Name");
        String imageUrl = getIntent().getStringExtra("Image URL");
        TextView textUser = findViewById(R.id.display_text_user_name);
        TextView textPet = findViewById(R.id.display_text_pet_name);
        ImageView imagePic = findViewById(R.id.display_image_profile_pic);
        textUser.setText(userName);
        textPet.setText(petName);
        Picasso.get().load(imageUrl).into(imagePic);
    }
}