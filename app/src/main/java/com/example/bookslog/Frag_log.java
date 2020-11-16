package com.example.bookslog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Frag_log extends Fragment {
    ImageView book_cover;
    Button log_save;
    TextView log_date;
    int tYear, tMonth, tDay;
    String[] options = {"갤러리에서 사진 가져오기","사진찍기", "취소"};
    final static int GO_GALLERY =1, TAKE_PIC =2, CANCEL=3;

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
        book_cover = fragview.findViewById(R.id.book_cover);
        Calendar cal = Calendar.getInstance();
        tYear = cal.get(Calendar.YEAR);
        tMonth = cal.get(Calendar.MONTH)+1;
        tDay = cal.get(Calendar.DAY_OF_MONTH);
        log_date.setText("작성일: "+tYear+"/"+tMonth+"/"+tDay);
        registerForContextMenu(book_cover);

        return fragview;
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, GO_GALLERY ,0,options[0]);
        menu.add(0,TAKE_PIC,0,options[1]);
        menu.add(0,CANCEL,0,options[2]);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case GO_GALLERY:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK ,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto,1);
                break;
            case TAKE_PIC:
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture,0);
        }
        return super.onContextItemSelected(item);
    }

    public void showToast(String msg){
        //토스트 메시지 화면 중간에 뜸
        Toast toast = Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();

    }
};


