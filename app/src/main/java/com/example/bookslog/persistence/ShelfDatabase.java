package com.example.bookslog.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookslog.Shelf_items;

@Database(entities = {Shelf_items.class}, version = 1)
public abstract class ShelfDatabase extends RoomDatabase {

/*    public static final String DATABASE_NAME = "shelf_db";

    private static ShelfDatabase instance;

    static ShelfDatabase getInstance(final Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ShelfDatabase.class,
                    DATABASE_NAME).build();
        }
        return instance;
    }*/
    public abstract ShelfDao getShelfDao();
}
