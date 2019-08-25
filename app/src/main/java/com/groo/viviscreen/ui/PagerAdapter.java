package com.groo.viviscreen.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentHashMap.get(position) != null) {
            return fragmentHashMap.get(position);
        }

        switch (position) {
            case 0:
                ProfileFragment tab1 = new ProfileFragment();
                fragmentHashMap.put(position, tab1);
                return tab1;
            case 1:
                ADFragment tab2 = new ADFragment();
                fragmentHashMap.put(position, tab2);
                return tab2;
            case 2:
                PointFragment tab3 = new PointFragment();
                fragmentHashMap.put(position, tab3);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}