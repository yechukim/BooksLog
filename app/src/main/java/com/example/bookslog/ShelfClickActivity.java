package com.example.bookslog;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

public class ShelfClickActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String TAG = "fab";
    //ui 요소
    private LineEditText write;
    private EditText title, author;
    private RatingBar ratingBar;
    private ImageView bcover;
    private TextView writeDate;
    private Button btnSave;
  //변수
    private boolean mIsNewBook;
    private Shelf_items mInitialBook;
    private GestureDetector mGestureDetector;
    int tYear, tMonth, tDay;

    //db
    MyHelper myHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_click);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("책꽂이로 돌아가기");
        ab.setDisplayHomeAsUpEnabled(true);
        myHelper = new MyHelper(getApplicationContext());
        write = findViewById(R.id.write);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        bcover = findViewById(R.id.bcover);
        ratingBar = findViewById(R.id.ratingBar);
        writeDate = findViewById(R.id.writeDate);


        Calendar cal = Calendar.getInstance();
        tYear = cal.get(Calendar.YEAR);
        tMonth = cal.get(Calendar.MONTH) + 1;
        tDay = cal.get(Calendar.DAY_OF_MONTH);
        btnSave = findViewById(R.id.btnSave);
        writeDate.setText(tYear + "/" + tMonth + "/" + tDay);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().isEmpty()) {
                    showToast("책 제목은 필수 입력항목입니다.");
                } else {
                    bookInsert();
                }
            }
        });
        if (getIncomingIntent()) {
            //새 책
            setNewBookProperties();
        } else {
            //새 책 아님
            setBookProperties();
        }
        setListeners();
    }
     //db 저장
     void bookInsert() {
        db = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookShelf.BookEntry.COL_NAME_TITLE, title.getText().toString());
        values.put(BookShelf.BookEntry.COL_NAME_AUTHOR, author.getText().toString());
        values.put(BookShelf.BookEntry.COL_NAME_CONTENT, write.getText().toString());
        values.put(BookShelf.BookEntry.COL_NAME_RATING, ratingBar.getRating());
        values.put(BookShelf.BookEntry.COL_NAME_WRITE_DATE, writeDate.getText().toString());
        db.insert(BookShelf.BookEntry.TBL_NAME, null, values);
        showToast("책꽂이에  저장되었습니다.");

        //리사이클러뷰에 바로 추가
         sendIntent();
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setListeners() {
        write.setOnTouchListener(this);
        title.setOnTouchListener(this);
        author.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_book")) {
            mInitialBook = getIntent().getParcelableExtra("selected_book");
            mIsNewBook = false;
            return false;
        }
        mIsNewBook = true;
        return true;
    }

    private void setBookProperties() {
        title.setText(mInitialBook.getBookTitle());
        author.setText(mInitialBook.getAuthor());
        writeDate.setText(mInitialBook.getWriteDate());
        write.setText(mInitialBook.getWrite());
        ratingBar.setRating(mInitialBook.getRatingBar());
        btnSave.setText("다시 저장하기");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"수정되었습니다.",Toast.LENGTH_SHORT).show();
                //db update
                SQLiteDatabase db = myHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BookShelf.BookEntry.COL_NAME_TITLE, title.getText().toString());
                values.put(BookShelf.BookEntry.COL_NAME_AUTHOR, author.getText().toString());
                values.put(BookShelf.BookEntry.COL_NAME_CONTENT, write.getText().toString());
                values.put(BookShelf.BookEntry.COL_NAME_RATING, ratingBar.getRating());
                values.put(BookShelf.BookEntry.COL_NAME_WRITE_DATE, writeDate.getText().toString());

                String selection = BookShelf.BookEntry.COL_NAME_TITLE + " LIKE ?";
                String[] selectionArgs = { mInitialBook.getBookTitle() };
                db.update(BookShelf.BookEntry.TBL_NAME,values,selection, selectionArgs);

            }
        });
    }

    private void setNewBookProperties() {
        title.setHint("제목을 입력하세요.");
        author.setHint("저자를 입력하세요.");
        write.setHint("기록하고 싶은 내용을 입력하세요.");
    }
    //리사이클러뷰 결과 바로 반영되게
    private void sendIntent(){
        Intent intent = new Intent(getApplicationContext(),Frag_shelf.class);
        intent.putExtra("title",title.getText().toString());
        intent.putExtra("author",author.getText().toString());
        intent.putExtra("content",write.getText().toString());
        intent.putExtra("rate",ratingBar.getRating());
        intent.putExtra("date",writeDate.getText().toString());
        setResult(RESULT_OK,intent);
        Log.d(TAG, "intent sent ");
        finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    //2번 탭하여 수정하기
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        onInteraction();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private boolean onInteraction() {
        write.setFocusableInTouchMode(true);
        author.setFocusableInTouchMode(true);
        title.setFocusableInTouchMode(true);
        return true;
    }

    private void offInteraction() {
        write.setFocusable(false);
        write.clearFocus();

        author.setFocusable(false);
        author.clearFocus();

        title.setFocusable(false);
        title.clearFocus();
    }

    @Override
    public void onBackPressed() {
        if (onInteraction()) {
            offInteraction();
        }
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        myHelper.close();
        super.onDestroy();
    }
}