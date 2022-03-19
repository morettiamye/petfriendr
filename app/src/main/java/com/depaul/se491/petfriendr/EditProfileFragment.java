package com.depaul.se491.petfriendr;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.depaul.se491.petfriendr.models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditProfileFragment extends Fragment {
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private ImageView imageProfile;
    private EditText profileName, profileComment, email, password;

    private Button updateProfile, uploadPhoto;
    StorageReference storageRef;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUsersRef = mDatabase.child("users").child(user.getUid());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();

        // Register image capture activity callback
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleCameraResult);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleGalleryResult);

        profileName = view.findViewById(R.id.editProfileName_textField);
        profileComment = view.findViewById(R.id.editProfile_textField);
        password = view.findViewById(R.id.editPassword_textField);
        email = view.findViewById(R.id.updateEmail_textField);



        imageProfile = view.findViewById(R.id.imageProfile);
        updateProfile = view.findViewById(R.id.updateProfile);
        uploadPhoto = view.findViewById(R.id.uploadProfilePic_button);

        imageProfile.setOnClickListener(v -> showDialogGetProfileImage());
        updateProfile.setOnClickListener(v -> showDialogSaveChanges());
        uploadPhoto.setOnClickListener(v -> uploadNewPhoto());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void showDialogGetProfileImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.imagepreview);
        builder.setNeutralButton(R.string.cancel, (dialog, which) ->  { });
        builder.setNegativeButton(R.string.gallery, (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });
        builder.setPositiveButton(R.string.camera, (dialog, which) -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        });
        builder.show();
    }

    private void handleCameraResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            try {
                Intent data = result.getData();
                assert data != null;
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageProfile.setImageBitmap(imageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleGalleryResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            try {
                Intent data = result.getData();
                assert data != null;
                Uri imageUri = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageProfile.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showDialogSaveChanges() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.saveChanges);
        builder.setNegativeButton(R.string.cancel, (dialog, which) ->  { });
        builder.setPositiveButton(R.string.ok, (dialog, which) -> storeUserInfo());
        builder.show();

    }


    private void storeUserInfo() {

        String newProfileName = profileName.getText().toString();
        String newProfileComment = profileComment.getText().toString();
        String newPassword = password.getText().toString();
        String newEmail = email.getText().toString();


        mUsersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                UserProfile newUser = new UserProfile("",newProfileName,user.getUid(),user.getPhotoUrl().toString(),newProfileComment);
                mDatabase.child("users").child(user.getUid()).setValue(newUser);
                user.updateEmail(newEmail);
                user.updatePassword(newPassword);


            }
        });
    }
    private void uploadNewPhoto(){
        String uid = user.getUid();
        StorageReference profilepicImageRef = storageRef.child("images/"+uid);
        imageProfile.setDrawingCacheEnabled(true);
        imageProfile.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageProfile.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = profilepicImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference storageRefUpload = FirebaseStorage.getInstance().getReference();
                String link = profilepicImageRef.getDownloadUrl().toString();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse(link))
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                }
                            }
                        });

            }
        });



    }
}