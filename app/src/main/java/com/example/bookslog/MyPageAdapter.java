package com.example.bookslog;

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
        super(fm,numOfTabs); //이렇게 해도 똑같은지 보기
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Frag_shelf frag_shelf = new Frag_shelf();
                return frag_shelf;
            case 1:
                Frag_cal frag_cal = new Frag_cal();
                return frag_cal;
            case 2:
                Frag_log frag_log = new Frag_log();
                return frag_log;
            case 3:
                Frag_statistic frag_statistic = new Frag_statistic();
                return frag_statistic;
            case 4:
                Frag_best frag_best = new Frag_best();
                return frag_best;
            default:
                return null;
        }
    }
}
