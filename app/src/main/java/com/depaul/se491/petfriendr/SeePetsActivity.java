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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.depaul.se491.petfriendr.models.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeePetsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private PetProfileAdapter profileAdapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsersRef;
    private ValueEventListener mUsersListener;

    private ArrayList<UserProfile> profileList;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUsers();
    }

    @Override
    public void onClick(View view) {
        UserProfile profile = profileList.get((recyclerView.getChildAdapterPosition(view)));
        Intent intent = new Intent(this, DisplayProfileActivity.class);
        intent.putExtra("User Name", profile.getUserName());
        intent.putExtra("Pet Name", profile.getPetName());
        intent.putExtra("Image URL", profile.getPhoto());
        intent.putExtra("Message", profile.getProfileMessage());
        intent.putExtra("userId",profile.getUserId());
        startActivity(intent);
    }



    @Override
    public void onStop() {
        super.onStop();
        cleanBasicListener();

    }
    public void getUsers() {
        mUsersRef = mDatabase.child("users");
        mUsersListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText( SeePetsActivity.this,"data retrieved",Toast.LENGTH_LONG);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    UserProfile user = child.getValue(UserProfile.class);
                    profileList.add(user);
                    profileAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( SeePetsActivity.this,"data failed",Toast.LENGTH_LONG);
            }
        };
        mUsersRef.addValueEventListener(mUsersListener);

    }
    public void cleanBasicListener() {

        mUsersRef.removeEventListener(mUsersListener);
    }

}
