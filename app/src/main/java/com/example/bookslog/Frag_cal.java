package com.example.bookslog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class Frag_cal extends Fragment {
    TextView selectedDate;
    CalendarView calendar;
    int tYear, tMonth, tDay;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_frag_cal, container, false);

        calendar = fragView.findViewById(R.id.calendar);
        selectedDate = fragView.findViewById(R.id.selectedDate);
        //오늘 날짜가져와서 디폴트로 뜨게하기
        Calendar cal = Calendar.getInstance();
        tYear = cal.get(Calendar.YEAR);
        tMonth = cal.get(Calendar.MONTH)+1;
        tDay = cal.get(Calendar.DAY_OF_MONTH);
        selectedDate.setText(tYear + "/" + tMonth + "/" + tDay);
        //날짜 선택시 날짜가 뜸
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                selectedDate.setText(year + "/" + month + "/" + dayOfMonth);
            }
        });

        //해당 날짜에 책이 있으면 가져옴
        return fragView;
    }



}