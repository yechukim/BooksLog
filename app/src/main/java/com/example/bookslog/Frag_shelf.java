package com.example.bookslog;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Frag_shelf extends Fragment implements ShelfAdapter.OnShelfListener,
        View.OnClickListener {
    private static final String TAG = "tag";

    //UI components
    RecyclerView mRecyclerView;
    FloatingActionButton fab;

    //vars
    private ArrayList<Shelf_items> mShelf = new ArrayList<>();
    ShelfAdapter mShelfAdapter;

    SQLiteDatabase db;
    MyHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_frag_shelf, container, false);
        mRecyclerView = fragView.findViewById(R.id.recyclerView);
        helper = new MyHelper(getContext());
        db = helper.getReadableDatabase();
        //fab
        fab = fragView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);
        mShelfAdapter = new ShelfAdapter(getContext(), this, mShelf);
        mRecyclerView.setAdapter(mShelfAdapter);
        setHasOptionsMenu(true);
        //db insert book
        insertBooks();


        return fragView;
    }

    private void insertBooks() {

        String[] projection = {
                BookShelf.BookEntry.COL_NAME_TITLE,
                BookShelf.BookEntry.COL_NAME_AUTHOR,
                BookShelf.BookEntry.COL_NAME_CONTENT,
                BookShelf.BookEntry.COL_NAME_RATING,
                BookShelf.BookEntry.COL_NAME_WRITE_DATE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = BookShelf.BookEntry.COL_NAME_TITLE + " = ?";
        String[] selectionArgs = {"My Title"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BookShelf.BookEntry.COL_NAME_WRITE_DATE + " DESC";

        Cursor cursor = db.query(
                BookShelf.BookEntry.TBL_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        Shelf_items items = new Shelf_items();
        //db에서 가져오기
        while (cursor.moveToNext()) {
            items.setBookTitle(cursor.getString(0));
            items.setAuthor(cursor.getString(1));
            items.setWrite(cursor.getString(2));
            items.setRatingBar(cursor.getFloat(3));
            items.setWriteDate(cursor.getString(4));
            mShelf.add(items);
            Log.d(TAG, "insertBooks: "+cursor.getString(0));
        }

        mShelfAdapter.notifyDataSetChanged();
        cursor.close();
    }


    // 프래그먼트마다 액션바 다르게 구성..
    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //카드뷰 클릭
    @Override
    public void onShelfClick(int position) {
        Intent intent = new Intent(getActivity(), ShelfClickActivity.class);
        intent.putExtra("selected_book", mShelf.get(position));
        startActivity(intent);
    }

    // 새로 기록하기
    @Override
    public void onClick(View v) {
        fab.setImageResource(R.drawable.write_selected);
        Intent intent = new Intent(getActivity(), ShelfClickActivity.class);
        startActivity(intent);
    }

    private void deleteBooks(Shelf_items book) {
        mShelf.remove(book);
        mShelfAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }

    //스와이프하여 삭제
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {//오른쪽으로 스와이프해서 삭제
        //이건 드래그하고 싶을 때
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteBooks(mShelf.get(viewHolder.getAdapterPosition()));
        }
    };
}
