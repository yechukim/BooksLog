package com.example.bookslog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPageAdapter extends FragmentPagerAdapter {
    int numOfTabs;

    @Override
    public int getCount() {
        return numOfTabs;
    }

    public MyPageAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Frag_shelf();
            case 1:
                return new Frag_cal();
            case 2:
                return new Frag_statistic();
            case 3:
                return new Frag_best();
            default:
                return null;
        }
    }
}
