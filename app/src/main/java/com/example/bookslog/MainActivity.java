package com.example.bookslog;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager viewPager;
    MyPageAdapter adapter;
    int[] tabUnselectedImg ={R.drawable.shelf, R.drawable.cal, R.drawable.write, R.drawable.statistics, R.drawable.best};
    int[] tabSelectedImg = {R.drawable.bk_selected, R.drawable.cal_selected, R.drawable.write_selected, R.drawable.statistics_selected, R.drawable.best_selected};
    String[] barTitle = {"책꽂이", "북스로그 달력", "기록하기", "북스로그 통계", "베스트 셀러"};
    int position; //탭 포지션 가져오게 만들기
    ActionBar actionBar;

    //액션바 검색 아이콘
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //검색 아이콘 객체 참조
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar,menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // 여기에 이벤트 리스너 설정..?
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        tabs.addTab(tabs.newTab().setIcon(tabSelectedImg[0]));
        for(int i=1; i<tabSelectedImg.length; i++){
            tabs.addTab(tabs.newTab().setIcon(tabUnselectedImg[i]));
        }
        tabs.setTabGravity(tabs.GRAVITY_FILL);
        //프래그먼트 미리 담아놓기
        viewPager.setOffscreenPageLimit(5);
        //액션바
        actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=\"#494949\">" + barTitle[0] + "</font>"));//액션바 텍스트 색상

      //  actionBar.setSubtitle("책꽂이에서 기록 검색");


        //어댑터 설정
        adapter = new MyPageAdapter(getSupportFragmentManager(), 5);//프래그먼트 매니저 참
        //어댑터 객체 장착
        viewPager.setAdapter(adapter);

        //탭메뉴를 누를 때
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //뷰페이지 바꿀 때
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                actionBar.setTitle(Html.fromHtml("<font color=\"#494949\">" + barTitle[position] + "</font>"));
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

}