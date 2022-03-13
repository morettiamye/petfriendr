package com.depaul.se491.petfriendr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PetProfileAdapter extends RecyclerView.Adapter<PetProfileHolder> {

    private SeePetsActivity activity;

    private ArrayList<PetProfile> profileList;

    public PetProfileAdapter(SeePetsActivity activity, ArrayList<PetProfile> profileList) {
        this.activity = activity;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public PetProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_pets, parent, false);
        view.setOnClickListener(activity);
        return new PetProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetProfileHolder holder, int position) {
        PetProfile profile = profileList.get(position);
        // TODO: Set Profile Image
        holder.setProfileImage();
        holder.setUserName(profile.getUserName());
        holder.setPetName(profile.getPetName());
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
