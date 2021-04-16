package com.example.xcargomobile.userscreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.request.RequestOptions;
import com.example.xcargomobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {

    private static final String TAG = "ProfileSettings";

    private EditText profile_name, profile_lastname, profile_email;
    private Button profile_update_btn, logout_btn, changeProfile;
    private Button resetPassLocal,changeProfileImage;
    private CircleImageView profile_image;
    private Toolbar settings_toolbar;
    private Uri imageUri;

    private Boolean IsCheck = false;
    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    int TAKE_IMAGE_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings);

        profile_name = findViewById(R.id.profile_name);
        profile_lastname = findViewById(R.id.profile_lastname);
        profile_email = findViewById(R.id.profile_email);
        profile_image = findViewById(R.id.profile_image);

        profile_update_btn = findViewById(R.id.profile_update_btn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        final String userID = fAuth.getCurrentUser().getUid();

        /*
        DocumentReference washingtonRef = fStore.collection("users").document(userID);

        fStore.collection("users").document(userID)
                .update(
                        "name", "Murat"
                );
*/
        fStore.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String user_name = task.getResult().getString("name");
                    String user_lastname = task.getResult().getString("lastname");
                    String user_email = task.getResult().getString("email");
                    String user_image = task.getResult().getString("image");
                    imageUri = Uri.parse(user_image);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.ff_musk4_f_2);

                    //Glide.with(Settings.this).setDefaultRequestOptions(requestOptions).load(imageUri).into(profile_image);
                    profile_name.setText(user_name);
                    profile_lastname.setText(user_lastname);
                    profile_email.setText(user_email);
                }

            }
        });

        profile_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = profile_name.getText().toString();
                final String lastname = profile_lastname.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastname)){

                    Map userUpdateMap = new HashMap();
                    userUpdateMap.put("name", name);
                    userUpdateMap.put("lastname", lastname);

                    fStore.collection("users").document(userID).update(userUpdateMap).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Settings.this, "Update :D", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Settings.this, "Not Update :D", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    public void handleImageClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

 /*
      public void handleImageClick(View view) {
        ActivityCompat.requestPermissions(Settings.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE){
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    profile_image.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }
        }
    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("image");
                profile_image.setImageBitmap(bitmap);
                handleUpload(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference()
            .child("image")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NotNull Exception e) {
                Log.e(TAG, "onFailure",e.getCause());
            }
        });
    }

    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e(TAG, "onSuccess" + uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid){
                        Toast.makeText(Settings.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NotNull Exception e) {
                        Toast.makeText(Settings.this, "Profile image failed...", Toast.LENGTH_SHORT);
                    }
                });
    }



}


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                profile_image.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    */