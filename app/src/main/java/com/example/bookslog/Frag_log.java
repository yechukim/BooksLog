package com.example.bookslog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Frag_log extends Fragment {
    Button log_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragview = inflater.inflate(R.layout.fragment_frag_log, container, false);
        log_save = fragview.findViewById(R.id.log_save);
        log_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showToast("책꽂이에 저장되었습니다.");

            }
        });
        return fragview;
    }

    public void showToast(String msg){
        //토스트 메시지 화면 중간에 뜸
        Toast toast = Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();

    }
};


