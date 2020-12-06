package com.example.bookslog;

import android.provider.BaseColumns;

public final class BookShelf{
    private BookShelf(){}

    public static class BookEntry implements BaseColumns {
        public static final String TBL_NAME = "shelf";
        public static final String COL_NAME_TITLE = "title";
        public static final String COL_NAME_AUTHOR = "author";
        public static final String COL_NAME_CONTENT = "content";
        public static final String COL_NAME_RATING = "rating";
        public static final String COL_NAME_WRITE_DATE = "date";
    }
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookEntry.TBL_NAME + " ("+
                    BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                    BookEntry.COL_NAME_TITLE +" TEXT, " +
                    BookEntry.COL_NAME_AUTHOR + " TEXT, " +
                    BookEntry.COL_NAME_CONTENT + " TEXT, " +
                    BookEntry.COL_NAME_RATING + " INTEGER, " +
                    BookEntry.COL_NAME_WRITE_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BookEntry.TBL_NAME;
}

