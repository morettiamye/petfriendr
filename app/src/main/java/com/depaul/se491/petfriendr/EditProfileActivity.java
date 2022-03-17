/*
        This will allow the user to edit their profile.  This screen will contain:
            - Field for pet name
            - Field for Profile message
            - Image Uploader
            - Field to update email
            - Field to update password

            All of the above should be send to database as update to current information
 */

package com.depaul.se491.petfriendr;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private ImageView imageProfile;
    private Button updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Register image capture activity callback
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleCameraResult);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleGalleryResult);

        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        updateProfile = (Button) findViewById(R.id.updateProfile);
        imageProfile.setOnClickListener(v -> showDialogGetProfileImage());
        updateProfile.setOnClickListener(v -> showDialogSaveChanges());
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
}