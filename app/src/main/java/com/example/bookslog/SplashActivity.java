package com.example.bookslog;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(2000); //splash 생성되고 3초간 일시정지
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish(); // splash 는 종료
    }
}
