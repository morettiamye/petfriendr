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
import android.widget.TextView;

import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {

    // Resource ID
    private final static int LAYOUT_EDIT_PROFILE = 0;
    private final static int EDIT_TEXT_YOUR_NAME = 0;
    private final static int EDIT_TEXT_PET_NAME = 0;
    private final static int EDIT_TEXT_PET_AGE = 0;
    private final static int EDIT_TEXT_LOCATION = 0;
    private final static int EDIT_TEXT_MESSAGE = 0;
    private final static int IMAGE_VIEW_PROFILE = 0;
    private final static int BUTTON_SUBMIT = 0;
    private final static int STRING_IMAGE_PROFILE = 0;
    private final static int STRING_GALLERY = 0;
    private final static int STRING_CAMERA = 0;
    private final static int STRING_SAVE_CHANGES = 0;
    private final static int STRING_CANCEL = 0;
    private final static int STRING_OK = 0;

    // Text View
    private TextView textYourName;
    private TextView textPetName;
    private TextView textPetAge;
    private TextView textLocation;
    private TextView textMessage;

    // Image View
    private ImageView imageProfile;

    // Button
    private Button buttonSubmit;

    // Activity Result Launcher
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        // Find view by ID
        textYourName = findViewById(EDIT_TEXT_YOUR_NAME);
        textPetName = findViewById(EDIT_TEXT_PET_NAME);
        textPetAge = findViewById(EDIT_TEXT_PET_AGE);
        textLocation = findViewById(EDIT_TEXT_LOCATION);
        textMessage = findViewById(EDIT_TEXT_MESSAGE);
        imageProfile = findViewById(IMAGE_VIEW_PROFILE);
        buttonSubmit = findViewById(BUTTON_SUBMIT);
        assert textYourName != null;
        assert textPetName != null;
        assert textPetAge != null;
        assert textLocation != null;
        assert textMessage != null;
        assert imageProfile != null;
        assert buttonSubmit != null;

        // Register image capture activity callback
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleCameraResult);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleGalleryResult);

        // Add image click listener
        imageProfile.setOnClickListener(view -> showDialogGetProfileImage());

        // Add button click listener
        buttonSubmit.setOnClickListener(view -> showDialogSaveChanges());
    }

    private void showDialogGetProfileImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(STRING_IMAGE_PROFILE);
        builder.setNeutralButton(STRING_CANCEL, (dialog, which) ->  { });
        builder.setNegativeButton(STRING_GALLERY, (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });
        builder.setPositiveButton(STRING_CAMERA, (dialog, which) -> {
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
        builder.setTitle(STRING_SAVE_CHANGES);
        builder.setNegativeButton(STRING_CANCEL, (dialog, which) ->  { });
        builder.setPositiveButton(STRING_OK, (dialog, which) -> storeUserInfo());
        builder.show();
    }

    private void storeUserInfo() {
    }
}