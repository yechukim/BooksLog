package com.example.bookslog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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

         adapter = new MyPageAdapter(getSupportFragmentManager(),5);
         viewPager.setAdapter(adapter);
         tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

    }
}