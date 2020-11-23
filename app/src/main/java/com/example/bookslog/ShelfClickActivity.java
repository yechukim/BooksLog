package com.example.bookslog;

import android.content.Intent;
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
import androidx.room.Room;

import com.example.bookslog.persistence.ShelfDao;
import com.example.bookslog.persistence.ShelfDatabase;

import java.util.Calendar;

public class ShelfClickActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    ShelfDatabase db;
    private static final String TAG = "tap";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_click);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("책꽂이로 돌아가기");
        ab.setDisplayHomeAsUpEnabled(true);
        db= Room.databaseBuilder(getApplicationContext(), ShelfDatabase.class, "shelf_db").allowMainThreadQueries().build();


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
                if (getIncomingIntent()) {
                    db.getShelfDao().insertBooks(new Shelf_items(
                            title.getText().toString(),
                            author.getText().toString(),
                            write.getText().toString(),
                            ratingBar.getNumStars(),
                            writeDate.getText().toString()
                    ));

                    mInitialBook.setBookTitle(title.getText().toString());
                    mInitialBook.setAuthor(author.getText().toString());
                    mInitialBook.setWriteDate(writeDate.getText().toString());
                    mInitialBook.setWrite(write.getText().toString());
                    mInitialBook.setRatingBar(ratingBar.getNumStars());
                } else {
                    db.getShelfDao().update(new Shelf_items(
                            title.getText().toString(),
                            author.getText().toString(),
                            write.getText().toString(),
                            ratingBar.getNumStars(),
                            writeDate.getText().toString()
                    ));
                }
                Intent intent = new Intent(getApplicationContext(),Frag_shelf.class);
                intent.putExtra("saved_book",mInitialBook);
                startActivity(intent);
                Log.d(TAG, "onClick: ");

                //카드뷰 +1, setText

                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
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
    }

    private void setNewBookProperties() {
        title.setHint("제목을 입력하세요.");
        author.setHint("저자를 입력하세요.");
        write.setHint("기록하고 싶은 내용을 입력하세요.");
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
        Log.d(TAG, "onDoubleTap: tapped");
        onInteraction();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private boolean onInteraction() {
        write.setFocusableInTouchMode(true);
        write.setFocusable(true);

        author.setFocusableInTouchMode(true);
        author.setFocusable(true);

        title.setFocusableInTouchMode(true);
        title.setFocusable(true);
        return true;
    }

    private void offInteraction() {
        write.setFocusableInTouchMode(false);
        write.setFocusable(false);
        write.clearFocus();

        author.setFocusableInTouchMode(false);
        author.setFocusable(false);
        author.clearFocus();

        title.setFocusableInTouchMode(false);
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
}