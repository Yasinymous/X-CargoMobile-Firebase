package com.example.xcargomobile.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.xcargomobile.userscreen.Searchcargo;
import com.example.xcargomobile.userscreen.Profile;
import com.example.xcargomobile.userscreen.News;

public class ViewPagerAdapter
        extends FragmentPagerAdapter {

    public ViewPagerAdapter(
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
            fragment = new News();
        else if (position == 1)
            fragment = new Searchcargo();
        else if (position == 2)
            fragment = new Profile();

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Home";
        else if (position == 1)
            title = "Search Cargo";
        else if (position == 2)
            title = "Profile";
        return title;
    }
}
