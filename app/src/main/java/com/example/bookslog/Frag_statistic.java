package com.example.bookslog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Frag_statistic extends Fragment {
    private static final String TAG = "statistic";
    //ui
    Button btn_week, btn_month, btn_year;
    TextView during, bookCounts, wholeCounts;
    String txtDuring;
    int counts;
    String today, oneWeekBefore, oneMonthBefore, oneYearBefore;
    int smallCounts;
    ArrayList<Shelf_items> mShelf = new ArrayList<>();

    //db
    SQLiteDatabase db;
    MyHelper helper;

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_frag_statistic, container, false);
        btn_week = fragView.findViewById(R.id.btn_week);
        btn_month = fragView.findViewById(R.id.btn_month);
        btn_year = fragView.findViewById(R.id.btn_year);
        during = fragView.findViewById(R.id.during);
        bookCounts = fragView.findViewById(R.id.bookCounts);//읽은 책 권수
        wholeCounts = fragView.findViewById(R.id.wholeCounts);
        txtDuring = during.getText().toString();

        oneWeekBefore = getCalculatedDate("yyyy/M/d", -7);
        oneMonthBefore = getCalculatedDate("yyyy/M/d", -30);
        oneYearBefore = getCalculatedDate("yyyy/M/d", -365);
        today = getCalculatedDate("yyyy/M/d", 0);

        //db 1개당 1권... if title && author is same 제외

        helper = new MyHelper(getActivity());
        db = helper.getReadableDatabase();
        String[] projection = {
                BookShelf.BookEntry.COL_NAME_WRITE_DATE};
     /*   String selection = BookShelf.BookEntry.COL_NAME_WRITE_DATE + " LIKE ?";
        String[] selectionArgs = {today};*/

        Cursor cursor = db.query(
                BookShelf.BookEntry.TBL_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            counts = cursor.getCount(); // db 개수 - 읽은 책 수
            String oldString = cursor.getString(0);

            try {
                Shelf_items item = new Shelf_items();
                /*Date date = new SimpleDateFormat("yyyy/M/d").parse(oldString);
                Log.d(TAG, "onCreateView: "+date); // to date
                String newDate = new SimpleDateFormat("yyyy/M/d").format(date);
                Log.d(TAG, "onCreateView: "+newDate); // to string
                */
                Date todayDate = new SimpleDateFormat("yyyy/M/d").parse(today);
                Date weekAgo = new SimpleDateFormat("yyyy/M/d").parse(oneWeekBefore);


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }



        wholeCounts.setText("현재까지 총 " + String.valueOf(counts) + "권 읽었어요");
        //디폴트 값은 이번주 , 이번주에 읽은 책 수
        during.setText("7일" + txtDuring);

        //btn 클릭
        btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textWeek = btn_week.getText().toString();
                during.setText(textWeek + txtDuring);
            }
        });
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textMonth = btn_month.getText().toString();
                during.setText(textMonth + txtDuring);
            }
        });
        btn_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textYear = btn_year.getText().toString();
                during.setText(textYear + txtDuring);
            }
        });



        return fragView;
    }

    @Override
    public void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}

