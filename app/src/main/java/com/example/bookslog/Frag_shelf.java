package com.example.bookslog;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Frag_shelf extends Fragment implements ShelfAdapter.OnShelfListener,
        View.OnClickListener {
    private static final String TAG = "shelf";

    //UI components
    RecyclerView mRecyclerView;
    FloatingActionButton fab;

    //vars
    ArrayList<Shelf_items> mShelf = new ArrayList<>();
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
        //fab
        fab = fragView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setImageResource(R.drawable.add);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);
        mShelfAdapter = new ShelfAdapter(getContext(), this, mShelf);
        mRecyclerView.setAdapter(mShelfAdapter);
        setHasOptionsMenu(true);
        addBooks();
        return fragView;
    }

    public void addBooks() {

        helper = new MyHelper(getContext());
        db = helper.getReadableDatabase();

        String[] projection = {
                BookShelf.BookEntry.COL_NAME_TITLE,
                BookShelf.BookEntry.COL_NAME_AUTHOR,
                BookShelf.BookEntry.COL_NAME_CONTENT,
                BookShelf.BookEntry.COL_NAME_RATING,
                BookShelf.BookEntry.COL_NAME_WRITE_DATE};
        String sortOrder =
                BookShelf.BookEntry._ID + " DESC";

        Cursor cursor = db.query(
                BookShelf.BookEntry.TBL_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);

        //db에서 가져오기
        while (cursor.moveToNext()) {
            Shelf_items items = new Shelf_items();
            items.setBookTitle(cursor.getString(0));
            items.setAuthor(cursor.getString(1));
            items.setWrite(cursor.getString(2));
            items.setRatingBar(cursor.getInt(3));
            items.setWriteDate(cursor.getString(4));
            mShelf.add(items);
            Log.d(TAG, "added books on Shelf_items: " + items);
        }
        cursor.close();
    }

    // 프래그먼트마다 액션바 다르게 구성..
    @Override
    public void onResume() {
        super.onResume();
        mShelf.clear();
        addBooks();
        getActivity().invalidateOptionsMenu();
    }

/*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("책 제목으로 검색하기");

*/
/*        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //아이템 추가후 바로 삭제하면 오류남.. 왜 때문이지
              newText = newText.toLowerCase();
                ArrayList<Shelf_items> nShelf = new ArrayList<>();
                for (Shelf_items items : mShelf) {
                    String itemName = items.getBookTitle().toLowerCase();
                    if (itemName.contains(newText)) {
                        nShelf.add(items);
                    }
                    mShelfAdapter.filter(nShelf);
                }
                return true;
            }
        });
 *//*
   }
*/

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
        Intent intent = new Intent(getActivity(), ShelfClickActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            Shelf_items items = new Shelf_items();
            items.setBookTitle(data.getStringExtra("title"));
            items.setAuthor(data.getStringExtra("author"));
            items.setWrite(data.getStringExtra("content"));
            items.setRatingBar(data.getIntExtra("rate", 0));
            items.setWriteDate(data.getStringExtra("date"));
            mShelf.add(items);
            Log.d(TAG, "received intent " + items);
            mShelfAdapter.notifyDataSetChanged();
        }
    }

    //카드 삭제
    private void deleteBooks(Shelf_items book) {
        mShelf.remove(book);

    }//카드 복원
    private void undo(){

    }

    //db 삭제
    private void deleteDB(Shelf_items book){
        db = helper.getWritableDatabase();
        String selection = BookShelf.BookEntry.COL_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {book.getBookTitle()};
        db.delete(BookShelf.BookEntry.TBL_NAME, selection, selectionArgs);
    }
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {//오른쪽으로 스와이프해서 삭제

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        //오른쪽으로 스와이프하면 해당 아이템 삭제함
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final Shelf_items deletedBook = mShelf.get(viewHolder.getAdapterPosition());
            int deletedBookPosition = viewHolder.getAdapterPosition();
            deleteBooks(deletedBook);
            mShelfAdapter.notifyDataSetChanged();
            Snackbar snackbar = Snackbar.make(getView(), "기록이 삭제되었습니다.", Snackbar.LENGTH_LONG).setAnchorView(fab);
            snackbar.setAction(" 되돌리기", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar snackbar = Snackbar.make(getView(), "기록이 복원되었습니다.", Snackbar.LENGTH_LONG).setAnchorView(fab);
                    snackbar.show();
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

            snackbar.addCallback(new Snackbar.Callback(){

                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    if(event ==Snackbar.Callback.DISMISS_EVENT_TIMEOUT){
                        //스낵바 사라지면 db삭제
                        deleteDB(deletedBook);
                    }
                    super.onDismissed(transientBottomBar, event);
                }
            });

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}

