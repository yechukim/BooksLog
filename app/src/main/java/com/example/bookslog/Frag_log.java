package com.example.bookslog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Frag_log extends Fragment {
    Button log_save;
    TextView log_date;
    int tYear, tMonth, tDay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragview = inflater.inflate(R.layout.fragment_frag_log, container, false);
        log_save = fragview.findViewById(R.id.log_save);
        log_date = fragview.findViewById(R.id.log_date);
        log_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showToast("책꽂이에 저장되었습니다.");

            }
        });
        log_date = fragview.findViewById(R.id.log_date);
        Calendar cal = Calendar.getInstance();
        tYear = cal.get(Calendar.YEAR);
        tMonth = cal.get(Calendar.MONTH)+1;
        tDay = cal.get(Calendar.DAY_OF_MONTH);
        log_date.setText("작성일: "+tYear+"/"+tMonth+"/"+tDay);

        return fragview;
    }

    public void showToast(String msg){
        //토스트 메시지 화면 중간에 뜸
        Toast toast = Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();

    }
};


