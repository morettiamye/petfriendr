package com.depaul.se491.petfriendr;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetProfileHolder extends RecyclerView.ViewHolder {

    private ImageView imageProfile;
    private TextView textUserName;
    private TextView textPetName;

    public PetProfileHolder(@NonNull View itemView) {
        super(itemView);
        imageProfile = itemView.findViewById(R.id.image_recycler_profile);
        textUserName = itemView.findViewById(R.id.text_recycler_user_name);
        textPetName = itemView.findViewById(R.id.text_recycler_pet_name);
    }

    public void setProfileImage() {
        // TODO: Set profile image
    }

    public void setUserName(String text) {
        textUserName.setText(text);
    }

    public void setPetName(String text) {
        textPetName.setText(text);
    }
}
