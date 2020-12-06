package com.example.bookslog;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{
    TabLayout tabs;
    ViewPager viewPager;
    ActionBar actionBar;
    MyPageAdapter adapter;
    int[] tabImg ={R.drawable.shelf, R.drawable.cal, R.drawable.leaderboard, R.drawable.search_w};
    String[] barTitle = {"책꽂이", "북스로그 달력", "북스로그 통계", "책 검색 & 베스트 셀러"};
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setTitle(barTitle[0]);
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
      for(int i=0; i<tabImg.length; i++){
            tabs.addTab(tabs.newTab().setIcon(tabImg[i]));
        }
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        adapter = new MyPageAdapter(getSupportFragmentManager(), 4);//프래그먼트 매니저 참
        viewPager.setAdapter(adapter);
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                actionBar.setTitle(barTitle[position]);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(tabImg[position]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

}