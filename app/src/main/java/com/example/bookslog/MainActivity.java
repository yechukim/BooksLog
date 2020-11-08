package com.example.bookslog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager viewPager;
    MyPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        tabs.addTab(tabs.newTab().setIcon(R.drawable.bk2));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.cal3));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.write3));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.statistics));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.best2));
        tabs.setTabGravity(tabs.GRAVITY_FILL);
        //프래그먼트 미리 담아놓기
        viewPager.setOffscreenPageLimit(5);
        
         //어댑터 설정
         adapter = new MyPageAdapter(getSupportFragmentManager(),5);//프래그먼트 매니저 참
         //어댑터 객체 장착
         viewPager.setAdapter(adapter);
         //탭메뉴를 누를 때
         tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
         //뷰페이지 바꿀 때
         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

}