package com.example.xcargomobile.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.xcargomobile.MainActivity;
import com.example.xcargomobile.R;
import com.example.xcargomobile.fragment.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Signup extends AppCompatActivity {
    public static final String TAG = "TAG";
    private TextInputEditText sign_name, sign_lastname, sign_mail, sign_pass, sign_pass2;
    private Button signup_btn;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.signup);

        sign_name = findViewById(R.id.sign_name);
        sign_lastname = findViewById(R.id.sign_lastname);
        sign_mail = findViewById(R.id.sign_mail);
        sign_pass = findViewById(R.id.sign_pass);
        sign_pass2 = findViewById(R.id.sign_pass2);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        signup_btn = findViewById(R.id.signup_btn);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        }

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                final String email = sign_mail.getText().toString().trim();
                String password = sign_pass.getText().toString().trim();
                String password2 = sign_pass2.getText().toString().trim();
                final String name = sign_name.getText().toString();
                final String lastname = sign_lastname.getText().toString();

                if(TextUtils.isEmpty(email)){
                    sign_mail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    sign_pass.setError("Password is Required.");
                    return;
                }

                if(password==password2){
                    sign_pass.setError("Does not match");
                    sign_pass2.setError("Does not match");
                    return;
                }

                if(password.length() < 6){
                    sign_pass.setError("Password Must be >= 6 Characters");
                    return;
                }

                // register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // send verification link
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",name);
                            user.put("lastname",lastname);
                            user.put("image","default");
                            user.put("email",email);
                            user.put("status",0);
                            Toast.makeText(Signup.this, "User Created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Signup.this,Home.class);
                            startActivity(intent);

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Signup.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });


                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(Signup.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });

    }

}



