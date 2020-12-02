package com.example.bookslog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;

public class Frag_cal extends Fragment {
    private static final String TAG = "cal ";
    //ui
    TextView selectedDate, bookName;
    CalendarView calendar;
    int tYear, tMonth, tDay;
    ArrayList<Shelf_items> mShelf = new ArrayList<>();
    String i_result = "";
    String f_result = "";
    String tapDate;

    //db
    SQLiteDatabase db;
    MyHelper helper;
    Cursor cursor;
    Shelf_items item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_frag_cal, container, false);
        calendar = fragView.findViewById(R.id.calendar);
        selectedDate = fragView.findViewById(R.id.selectedDate);
        bookName = fragView.findViewById(R.id.bookName);

        //오늘 날짜가져와서 디폴트로 뜸
        Calendar cal = Calendar.getInstance();
        tYear = cal.get(Calendar.YEAR);
        tMonth = cal.get(Calendar.MONTH) + 1;
        tDay = cal.get(Calendar.DAY_OF_MONTH);
        selectedDate.setText(tYear + "/" + tMonth + "/" + tDay);

        //db 읽어오기 책 제목, 날짜
        helper = new MyHelper(getActivity());
        db = helper.getReadableDatabase();
        String[] projection = {
                BookShelf.BookEntry.COL_NAME_TITLE,
                BookShelf.BookEntry.COL_NAME_WRITE_DATE};

        cursor = db.query(
                BookShelf.BookEntry.TBL_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        //날짜 선택
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                    tapDate = year + "/" + (month + 1) + "/" + dayOfMonth;
                    selectedDate.setText(tapDate);
                    mShelf.clear();
                    f_result = "";
                    bookName.setText("기록없음");

                    if (cursor.moveToFirst()) {
                        do {
                            item = new Shelf_items();
                            item.setBookTitle(cursor.getString(0));
                            item.setWriteDate(cursor.getString(1));

                            if (item.getWriteDate().equals(tapDate)) {
                                mShelf.add(item);
                                Log.d(TAG, "onSelectedDayChange: item" + item);

                                for (Shelf_items i : mShelf) {
                                    i_result = i.getBookTitle() + " , ";
                                    Log.d(TAG, "onSelectedDayChange: i_result: " + i_result);
                                }
                                f_result += i_result;
                                bookName.setText(f_result);
                            }
                        } while (cursor.moveToNext());
                    }
                }

            });

            return fragView;

        }
    }
