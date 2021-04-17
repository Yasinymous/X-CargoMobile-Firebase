package com.example.xcargomobile.userscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.xcargomobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

public class Profile extends Fragment {

    public static final String TAG = "TAG";

    public Profile() {
        // Required empty public constructor
    }

    private CircleImageView profile_image;
    private Toolbar profile_toolbar;

    private Uri imageUri;

    private Boolean IsCheck = false;
    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    private TextView profile_fullnametext, profile_emailtext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.profile, container,false);

        profile_fullnametext = root.findViewById(R.id.profile_fullnametext);
        profile_emailtext = root.findViewById(R.id.profile_emailtext);
        profile_image = root.findViewById(R.id.profile_image);

        profile_toolbar = root.findViewById(R.id.settings_toolbar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        final String userID = fAuth.getCurrentUser().getUid();

        setHasOptionsMenu(true);

        profile_toolbar.setTitle("Profile");
        profile_toolbar.inflateMenu(R.menu.drawer_view);

        // and finally set click listener
        profile_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getActivity(),Settings.class);
                startActivity(intent);
                return true;
            }
        });

        fStore.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String user_name = task.getResult().getString("name");
                    String user_lastname = task.getResult().getString("lastname");
                    String user_email = task.getResult().getString("email");
                    String user_image = task.getResult().getString("image");
                    String full_name = user_name +" "+user_lastname;
                    imageUri = Uri.parse(user_image);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.avatar);
                    StorageReference dateRef = storageReference.child("image/" + userID+ ".jpg");
                    dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            Glide.with(getActivity()).setDefaultRequestOptions(requestOptions).load(downloadUrl).into(profile_image);
                        }

                    });

                    //Glide.with(getActivity()).setDefaultRequestOptions(requestOptions).load(imageUri).into(profile_image);
                    profile_fullnametext.setText(full_name);
                    profile_emailtext.setText(user_email);

                }

            }
        });

        return root;

    }

}



