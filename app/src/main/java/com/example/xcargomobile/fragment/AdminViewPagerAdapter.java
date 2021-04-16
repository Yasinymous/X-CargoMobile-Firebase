package com.example.xcargomobile.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.xcargomobile.admin.Adminhome;
import com.example.xcargomobile.admin.Searchcargolist;
import com.example.xcargomobile.userscreen.Searchcargo;
import com.example.xcargomobile.userscreen.Profile;


public class AdminViewPagerAdapter
        extends FragmentPagerAdapter {

    public AdminViewPagerAdapter(
            @NonNull FragmentManager fm)
    {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0)
            fragment = new Adminhome();
        else if (position == 1)
            fragment = new Searchcargolist();
        else if (position == 2)
            fragment = new Profile();

        return fragment;
    }

    @Override
    public int getCount() { return 3; }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Add Cargo";
        else if (position == 1)
            title = "Cargo List";
        else if (position == 2)
            title = "Profile";
        return title;
    }

}
