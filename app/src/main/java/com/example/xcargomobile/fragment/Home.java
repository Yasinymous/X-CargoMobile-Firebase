package com.example.xcargomobile.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.xcargomobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;

public class Home extends AppCompatActivity{

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    AdminViewPagerAdapter adminviewPagerAdapter;
    ProgressBar progressbarstatus;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private static String status;
    int progressStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        progressbarstatus = findViewById(R.id.progressbarstatus);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final String userID = fAuth.getCurrentUser().getUid();
        progressbarstatus.setVisibility(View.VISIBLE);

        fStore.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                   status = String.valueOf(task.getResult().get("status"));
                    progressbarstatus.setVisibility(View.INVISIBLE);
                    if(status.equals("0")){
                        //progressBar2.setVisibility(View.INVISIBLE);
                        viewPagerAdapter = new ViewPagerAdapter(
                                getSupportFragmentManager());
                        viewPager.setAdapter(viewPagerAdapter);

                        tabLayout.setupWithViewPager(viewPager);
                    }
                    else{
                        //progressBar2.setVisibility(View.INVISIBLE);
                        adminviewPagerAdapter = new AdminViewPagerAdapter(
                                getSupportFragmentManager());
                        viewPager.setAdapter(adminviewPagerAdapter);

                        tabLayout.setupWithViewPager(viewPager);
                    }
                }

            }
        });


    }

    
}

