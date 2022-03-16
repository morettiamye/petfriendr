package com.depaul.se491.petfriendr;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;


public class DBHelper extends AppCompatActivity {

    // upload file from local storage
//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageRef = storage.getReference();
//    Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//    StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
//    uploadTask = riversRef.putFile(file);
//
//// Register observers to listen for when the download is done or if it fails
//uploadTask.addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception exception) {
//            // Handle unsuccessful uploads
//        }
//    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//            // ...
//        }
//    });
//return url from file upload
//    final StorageReference ref = storageRef.child("images/mountains.jpg");
//    uploadTask = ref.putFile(file);
//
//    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//        @Override
//        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//            if (!task.isSuccessful()) {
//                throw task.getException();
//            }
//
//            // Continue with the task to get the download URL
//            return ref.getDownloadUrl();
//        }
//    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//        @Override
//        public void onComplete(@NonNull Task<Uri> task) {
//            if (task.isSuccessful()) {
//                Uri downloadUri = task.getResult();
//            } else {
//                // Handle failures
//                // ...
//            }
//        }
//    });

// full code to upload file and return uri also sets content type to image/jpeg
    // File or Blob
//    file = Uri.fromFile(new File("path/to/mountains.jpg"));

//// Create the file metadata
//    metadata = new StorageMetadata.Builder()
//            .setContentType("image/jpeg")
//        .build();
//
//// Upload file and metadata to the path 'images/mountains.jpg'
//    uploadTask = storageRef.child("images/"+file.getLastPathSegment()).putFile(file, metadata);
//
//// Listen for state changes, errors, and completion of the upload.
//uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//            Log.d(TAG, "Upload is " + progress + "% done");
//        }
//    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
//            Log.d(TAG, "Upload is paused");
//        }
//    }).addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception exception) {
//            // Handle unsuccessful uploads
//        }
//    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//            // Handle successful uploads on complete
//            // ...
//        }
//    });




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
