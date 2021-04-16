package com.example.xcargomobile.fragment;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.xcargomobile.R;
import com.example.xcargomobile.fragment.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity{

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    AdminViewPagerAdapter adminviewPagerAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        if(true){
            viewPagerAdapter = new ViewPagerAdapter(
                    getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);

            tabLayout.setupWithViewPager(viewPager);
        }
        else{
            adminviewPagerAdapter = new AdminViewPagerAdapter(
                    getSupportFragmentManager());
            viewPager.setAdapter(adminviewPagerAdapter);

            tabLayout.setupWithViewPager(viewPager);
        }
    }
    
}

