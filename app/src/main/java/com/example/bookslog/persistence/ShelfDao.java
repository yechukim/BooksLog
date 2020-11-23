package com.example.bookslog.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookslog.Shelf_items;

import java.util.List;

@Dao
public interface ShelfDao {

    //추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertBooks(Shelf_items... books); // ... = []

    @Query("SELECT * FROM shelf")
    LiveData<List<Shelf_items>> getBooks();

    //조회기능..
    @Query("SELECT * FROM shelf WHERE title LIKE :title")
    List<Shelf_items> getByTitleQuery(int title);

    //삭제
    @Delete
    int delete(Shelf_items... books);
    //업뎃
    @Update
    int update(Shelf_items... books);
}

