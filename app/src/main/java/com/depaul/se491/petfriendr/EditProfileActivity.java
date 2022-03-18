package com.depaul.se491.petfriendr;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private ImageView imageProfile;
    private Button updateProfile;
    private Button uploadPhoto;
    StorageReference storageRef;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;
    private DatabaseReference mUsersRef;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();

        // Register image capture activity callback
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleCameraResult);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleGalleryResult);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        updateProfile = (Button) findViewById(R.id.updateProfile);
        uploadPhoto = (Button) findViewById(R.id.uploadProfilePic_button);
        imageProfile.setOnClickListener(v -> showDialogGetProfileImage());
        updateProfile.setOnClickListener(v -> showDialogSaveChanges());
        uploadPhoto.setOnClickListener(v -> uploadNewPhoto());
    }


    private void showDialogGetProfileImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        if (result.getResultCode() == RESULT_OK) {
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
        if (result.getResultCode() == RESULT_OK) {
            try {
                Intent data = result.getData();
                assert data != null;
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageProfile.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showDialogSaveChanges() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.saveChanges);
        builder.setNegativeButton(R.string.cancel, (dialog, which) ->  { });
        builder.setPositiveButton(R.string.ok, (dialog, which) -> storeUserInfo());
        builder.show();
    }

    private void storeUserInfo() {
    }

    private void uploadNewPhoto(){
        String uid = user.getUid();
        StorageReference profilepicRef = storageRef.child(uid);
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
               String photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                 mDatabase.child("users").child(user.getUid()).child("photo").setValue(photo);

            }
        });



    }
}