package com.example.xcargomobile.fragment;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.xcargomobile.R;
import com.google.android.material.tabs.TabLayout;

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

        if(false){
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

