/*
    This screen will populate all of the profile entries in the database, display as cards.
        -Screen should be scrollable
        -Clicking on a card will take the user to the profile page
 */

package com.depaul.se491.petfriendr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeePetsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private PetProfileAdapter profileAdapter;

    private ArrayList<PetProfile> profileList;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_pets);
        profileList = new ArrayList<>();
        profileAdapter = new PetProfileAdapter(this, profileList);
        recyclerView = findViewById(R.id.recycler_pets);
        recyclerView.setAdapter(profileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Download pet profile data and add profiles to list
        profileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        PetProfile profile = profileList.get((recyclerView.getChildAdapterPosition(view)));
        Intent intent = new Intent(this, DisplayProfileActivity.class);
        intent.putExtra("User Name", profile.getUserName());
        intent.putExtra("Pet Name", profile.getPetName());
        // TODO: Send Profile Image with intent to next activity
        startActivity(intent);
    }
}