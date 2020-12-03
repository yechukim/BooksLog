package com.example.bookslog;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager viewPager;
    ActionBar actionBar;
    MyPageAdapter adapter;
    int[] tabUnselectedImg = {R.drawable.shelf, R.drawable.cal, R.drawable.statistics, R.drawable.best};
    int[] tabSelectedImg = {R.drawable.bk_selected, R.drawable.cal_selected, R.drawable.statistics_selected, R.drawable.best_selected};
    String[] barTitle = {"책꽂이", "북스로그 달력", "북스로그 통계", "베스트 셀러 & 책 검색"};
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
        tabs.addTab(tabs.newTab().setIcon(tabSelectedImg[0]));
        for (int i = 1; i < tabSelectedImg.length; i++) {
            tabs.addTab(tabs.newTab().setIcon(tabUnselectedImg[i]));
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
                tab.setIcon(tabSelectedImg[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(tabUnselectedImg[position]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    //2버 누르면 종료되게 나중에 설정하기
    public interface BackPressedListener {
        void onBackPressed();
    }
}

