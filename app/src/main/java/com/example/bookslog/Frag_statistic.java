package com.example.bookslog;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Frag_statistic extends Fragment {
    private static final String TAG = "stat";
    //ui
    TextView wholeCounts, firstLog, lastLog;
    int counts;
    String d1,d2, t1,t2;

    //db
    SQLiteDatabase db;
    MyHelper helper;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_graph, container, false);
        wholeCounts = fragView.findViewById(R.id.wholeCounts);
        firstLog = fragView.findViewById(R.id.firstLog);
        lastLog = fragView.findViewById(R.id.lastLog);

        //db
        helper = new MyHelper(getActivity());
        db = helper.getReadableDatabase();
        String[] projection = {
                BookShelf.BookEntry.COL_NAME_WRITE_DATE,
                BookShelf.BookEntry.COL_NAME_TITLE};

        cursor = db.query(
                BookShelf.BookEntry.TBL_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            counts = cursor.getCount(); // db 개수(책 수)
            if(cursor.getPosition()==0){
               d1 =  cursor.getString(0);
               t1 = cursor.getString(1);

            }else if(cursor.getPosition()==cursor.getCount()-1){
                d2 =  cursor.getString(0);
                t2 = cursor.getString(1);
            }
        }
        firstLog.setText("첫 기록: "+d1+", "+t1);
        lastLog.setText("마지막 기록: "+d2+", "+t2);

        if(counts==0){
            firstLog.setText("첫 기록: 기록없음");
            lastLog.setText("마지막 기록 : 기록없음");
        }else if(counts==1){
            lastLog.setText("마지막 기록: "+d1+", "+t1);
        }

        wholeCounts.setText("현재까지 총 " + String.valueOf(counts) + "권 읽었어요");
        return fragView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
            Log.d(TAG, "setUserVisibleHint: called");
        }
    }

    @Override
    public void onDestroy() {
        helper.close();
        super.onDestroy();
    }

}


