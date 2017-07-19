package com.reframe.lapp.adapters;

/**
 * Created by Aldo on 30-05-2017 to Lapp.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Aldo on 07-11-2016.
 */

public class MainNavigationAdapter extends FragmentPagerAdapter {

    private Context context;
    ArrayList<Fragment> pages = new ArrayList<>();

    public MainNavigationAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return pages.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    public void addPage(Fragment f)
    {
        pages.add(f);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
