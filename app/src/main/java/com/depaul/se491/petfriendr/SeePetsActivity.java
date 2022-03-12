/*
    This screen will populate all of the profile entries in the database, display as cards.
        -Screen should be scrollable
        -Clicking on a card will take the user to the profile page
 */

package com.depaul.se491.petfriendr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.depaul.se491.petfriendr.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class SeePetsActivity extends AppCompatActivity {

    //private final ArrayList<String> userList = new ArrayList<>();
    private Cards cards_data;
    private MyArrayAdapterClass myArrayAdapterClass;
    private int i;
    private FirebaseAuth firebaseAuth;//variable that stores everything about the logged in user.

    ListView listView;

    List<Cards> rowItems;
    private DatabaseReference usersDb;
    private String currentUId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_pets);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUId = firebaseAuth.getCurrentUser().getUid();

//        userList.add("Petfriendr user 1");
//        userList.add("Petfriendr user 2");
//        userList.add("Petfriendr user 3");
//        userList.add("Petfriendr user 4");
//        userList.add("Petfriendr user 5");
        rowItems = new ArrayList<Cards>();

        myArrayAdapterClass = new MyArrayAdapterClass(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(myArrayAdapterClass);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                myArrayAdapterClass.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Cards cards = (Cards) dataObject;
                String userID = cards.getThisUserID();
                usersDb.child(userID).child("connections").child("Nos").child(currentUId).setValue(true);
                Toast.makeText(SeePetsActivity.this, " ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards cards = (Cards) dataObject;
                String userID = cards.getThisUserID();
                usersDb.child(userID).child("connections").child("Yess").child(currentUId).setValue(true);
                isConnectionMatch(userID);
                Toast.makeText(SeePetsActivity.this, " ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
//                // Ask for more data here
//                //rowItems.add("XML ".concat(String.valueOf(i)));
//                myArrayAdapterClass.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });
        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener((itemPosition, dataObject) -> Toast.makeText(SeePetsActivity.this, "click", Toast.LENGTH_SHORT).show());
    }

    private void isConnectionMatch(String userID) {
        DatabaseReference currentUserConnectionsDB = usersDb.child(currentUId).child("connections").child("Yess").child(userID);
        currentUserConnectionsDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot dataSnapshot = null;
                if (dataSnapshot.exists()){
                    Toast.makeText(SeePetsActivity.this, "New Connection", Toast.LENGTH_SHORT).show();
                    usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).setValue(true);
                    usersDb.child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).setValue(true);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //displaying the potential matches
    public void getPotentialMatches(String userID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DataSnapshot dataSnapshot = null;
                if (dataSnapshot.exists() && !dataSnapshot.child("connections").child("Nos").hasChild(currentUId) && !dataSnapshot.child("connections").child("Yess").hasChild(currentUId)){
                    Cards item = new Cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString());
                    rowItems.add(item);
                    myArrayAdapterClass.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void logout(View view) {
        firebaseAuth.signOut();
        Intent intent  = new Intent(SeePetsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

//    public void goToSettings(View view) {
//        Intent intent  = new Intent(MainActivity.this, SettingsActivity.class);
//        startActivity(intent);
//        return;
//    }
}