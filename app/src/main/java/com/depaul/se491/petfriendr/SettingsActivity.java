//package com.example.petfrienderclone;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.net.URI;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SettingsActivity extends AppCompatActivity {
//
//    //import from activity_settings.xml
//    private EditText mNameField, mPhoneField;
//    private Button mBack, mConfirm;
//    private ImageView mProfileImage;
//
//    //variables to save the profile image URL to the database
//    private FirebaseAuth firebaseAuth;
//    private DatabaseReference databaseReference;
//    private  String userID, name,  phone,  profileImageURL;
//    private URI uri;
//    private Uri resultUri;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//
//        mNameField = (EditText) findViewById(R.id.name);
//        mPhoneField = (EditText) findViewById(R.id.phone);
//        mProfileImage = (ImageView) findViewById(R.id.profileImage);
//        mBack = (Button) findViewById(R.id.back);
//        mConfirm = (Button) findViewById(R.id.confirm);
//
//        //get the user ID
//        firebaseAuth = FirebaseAuth.getInstance();
//        userID = firebaseAuth.getCurrentUser().getUid();
//
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
//
//        //display
//        getUserInfo();
//        mProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);//not from the app it's from other features of the phone
//                intent.setType("image/*");
//                startActivity(intent);
//
//            }
//        });
//
//
//        //save info to the database
//        mConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveUserInformation();
//            }
//        });
//
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                return;
//            }
//        });
//
//    }
//
//    private void getUserInfo() {
//        //make a listener to check for current user info
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot snapshot) {
//                if (snapshot.exists() && snapshot.getChildrenCount()>0){
//                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
//                    if (map.get("name") !=null){
//                        //populate the mNameField with this names
//                        name = map.get("name").toString();
//                        mNameField.setText(name);
//
//                    }
//                    if (map.get("phone") !=null){
//                        //populate the phhone with this names
//                        phone = map.get("phone").toString();
//                        mPhoneField.setText(phone);
//
//                    }
//                    if (map.get("profileImage") !=null){
//                        //populate the profileImage with this names
//                        profileImageURL = map.get("profileImage").toString();
//                        Glide.with(getApplication()).load(profileImageURL).into(mProfileImage);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled( DatabaseError dataSnapshot) {
//
//            }
//        });
//    }
//
//    private void saveUserInformation() {
//        name = mNameField.getText().toString();
//        phone = mPhoneField.getText().toString();
//        Map userInfo = new HashMap();
//        userInfo.put("name",  name);
//        userInfo.put("phone", phone);
//        databaseReference.updateChildren(userInfo);
//        if(resultUri != null){
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile images").child(userID);//we will store all images underthe profile images
//            Bitmap bitmap = null;
//            //pass  the image that we got from the URI into the bitmap
//            try  {
//                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
//            byte [] data = byteArrayOutputStream.toByteArray();
//            UploadTask uploadTask = storageReference.putBytes(data);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    finish();
//
//                }
//            });
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    //grab the URL from the profile image
//                    //then will save
//                    //This URL allows us to get the image from the cloud in the future
//                    Uri downloadURL = taskSnapshot.getDownloadUrl();//9 30
//                    Map userInfo = new HashMap();
//                    userInfo.put("profileImage", downloadURL);
//                    databaseReference.updateChildren(userInfo);
//                    finish();
//                    return;
//
//                }
//            });
//
//        }
//        else {
//            finish();
//        }
//    }
//
//    @Override
//    public void onActivityReenter(int resultCode, Intent data) {
//        super.onActivityReenter(resultCode, data);
//        if(resultCode ==1 && resultCode== Activity.RESULT_OK){
//            final Uri imageUri = data.getData();
//            resultUri = imageUri;
//            mProfileImage.setImageURI(resultUri);
//
//        }
//    }
//
//
//}