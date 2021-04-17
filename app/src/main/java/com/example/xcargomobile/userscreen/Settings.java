package com.example.xcargomobile.userscreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
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

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.avatar);

                   // Glide.with(Settings.this).setDefaultRequestOptions(requestOptions).load(imageUri).into(profile_image);
                    StorageReference dateRef = storageReference.child("image/" + userID+ ".jpg");
                    dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            Glide.with(Settings.this).setDefaultRequestOptions(requestOptions).load(downloadUrl).into(profile_image);
                        }

                    });

                    //Glide.with(Settings.this)
                      //      .load(desertRef)
                        //    .into(profile_image);
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
                                Toast.makeText(Settings.this, "Update Profile", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Settings.this, "Not Update...", Toast.LENGTH_SHORT).show();
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

*/
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



    private void image_update(String userID){
        fStore.collection("users").document(userID)
                .update("image", userID+".jpeg").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Settings.this, "Update Profile Photo ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Settings.this, "Not Update.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleUpload(Bitmap bitmap){
        profile_image.setDrawingCacheEnabled(true);
        profile_image.buildDrawingCache();
        final String userID = fAuth.getCurrentUser().getUid();
        bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference desertRef = storageRef.child("image/"+userID+".jpg");
        UploadTask uploadTask = desertRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Settings.this, "Not Update.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image_update(userID);
            }
        });
    }

    private void image_delete(){
        final String userID = fAuth.getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference desertRef = storageRef.child("image/"+userID+".jpeg");
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }


    private void getDownloadUrl(StorageReference reference){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference desertRef = storageRef.child("image/"+userID+".jpeg");
        desertRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
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


/*
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

    */