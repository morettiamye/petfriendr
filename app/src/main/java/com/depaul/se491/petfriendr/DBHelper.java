package com.depaul.se491.petfriendr;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.net.Uri;


public class DBHelper extends AppCompatActivity {

    //allows access to user info and must be put into appropriate classes
//    private FirebaseAuth mAuth;
//    mAuth = FirebaseAuth.getInstance();
////check to see if user is logged in already
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            //reload(); code to update logged in user
//        }
//    }
//
//
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//    if(user !=null)    {
//            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
//
//            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//            String uid = user.getUid();
//
//        }

    // set user info code below
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//            .setDisplayName("Jane Q. User")
//            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg")) // change url to location of picture in bucket
//            .build();
//
//user.updateProfile(profileUpdates)
//            .addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            if (task.isSuccessful()) {
//                Log.d(TAG, "User profile updated.");
//            }
//        }
//    });

}
