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
    TextView wholeCounts;
    int counts;

    //db
    SQLiteDatabase db;
    MyHelper helper;
    Cursor cursor;
    ArrayList<Shelf_items> mShelf = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_graph, container, false);
        wholeCounts = fragView.findViewById(R.id.wholeCounts);
        //db
        helper = new MyHelper(getActivity());
        db = helper.getReadableDatabase();
        String[] projection = {
                BookShelf.BookEntry.COL_NAME_RATING,
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
            Shelf_items si = new Shelf_items();
            counts = cursor.getCount(); // db 개수(책 수)
            si.setRatingBar(cursor.getInt(0));
            si.setBookTitle(cursor.getString(1));
            mShelf.add(si);
            Log.d(TAG, "onCreateView: "+cursor.getString(0));
        }
        Log.d(TAG, "onCreateView: "+mShelf);

        //
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


