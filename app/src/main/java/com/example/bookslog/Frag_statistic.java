package com.example.bookslog;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Frag_statistic extends Fragment {
    Button btn_week, btn_month, btn_year;
    TextView during, bookCounts;
    String txtDuring;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_frag_statistic, container, false);
        btn_week = fragView.findViewById(R.id.btn_week);
        btn_month = fragView.findViewById(R.id.btn_month);
        btn_year = fragView.findViewById(R.id.btn_year);
        during =fragView.findViewById(R.id.during);
        bookCounts = fragView.findViewById(R.id.bookCounts);//읽은 책 권수
        txtDuring = during.getText().toString();
        //디폴트 값은 이번주 , 이번주에 읽은 책 수
        during.setText("이번 주"+txtDuring);
        // 이번주버튼 클릭
        btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textWeek = btn_week.getText().toString();
                during.setText(textWeek+txtDuring);
            }
        });
        // 한달 버튼 클릭
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textMonth = btn_month.getText().toString();
                during.setText(textMonth+txtDuring);
            }
        });
        //일년 버튼 클릭
        btn_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textYear = btn_year.getText().toString();
                during.setText(textYear+txtDuring);
            }
        });
        return fragView;
    }

}