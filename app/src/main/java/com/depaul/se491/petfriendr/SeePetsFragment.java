/*
    This screen will populate all of the profile entries in the database, display as cards.
        -Screen should be scrollable
        -Clicking on a card will take the user to the profile page
 */
package com.depaul.se491.petfriendr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.depaul.se491.petfriendr.models.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeePetsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private PetProfileAdapter profileAdapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsersRef;
    private ValueEventListener mUsersListener;

    private ArrayList<UserProfile> profileList;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_see_pets, container, false);

        profileList = new ArrayList<>();
        profileAdapter = new PetProfileAdapter(this, profileList);

        recyclerView = view.findViewById(R.id.recycler_pets);
        recyclerView.setAdapter(profileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUsers();
    }

    @Override
    public void onClick(View view) {
        UserProfile profile = profileList.get((recyclerView.getChildAdapterPosition(view)));
        Intent intent = new Intent(getActivity(), DisplayProfileActivity.class);
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
                Toast.makeText( getActivity(),"data retrieved",Toast.LENGTH_LONG);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    UserProfile user = child.getValue(UserProfile.class);
                    profileList.add(user);
                    profileAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( getActivity(),"data failed",Toast.LENGTH_LONG);
            }
        };
        mUsersRef.addValueEventListener(mUsersListener);

    }
    public void cleanBasicListener() {

        mUsersRef.removeEventListener(mUsersListener);
    }

}
