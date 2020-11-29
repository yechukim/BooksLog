package com.example.bookslog;

import android.os.Parcel;
import android.os.Parcelable;

public class Shelf_items implements Parcelable {
    private int id;
    private int bookCover;
    private String bookTitle;
    private String writeDate;
    private String author;
    private String write;
    private String editDate;
    private float ratingBar;


    public Shelf_items(String bookTitle, String author, String write, float ratingBar, String writeDate) {
        //this.bookCover = bookCover;
        this.bookTitle = bookTitle;
        this.author = author;
        this.write = write;
        this.ratingBar = ratingBar;
        this.writeDate = writeDate;
    }

    public Shelf_items() {

    }

    protected Shelf_items(Parcel in) {
        id = in.readInt();
        bookCover = in.readInt();
        bookTitle = in.readString();
        author = in.readString();
        writeDate = in.readString();
        editDate = in.readString();
        write = in.readString();
        ratingBar = in.readInt();
    }

    public static final Creator<Shelf_items> CREATOR = new Creator<Shelf_items>() {
        @Override
        public Shelf_items createFromParcel(Parcel in) {
            return new Shelf_items(in);
        }

        @Override
        public Shelf_items[] newArray(int size) {
            return new Shelf_items[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookCover() {
        return bookCover;
    }

    public void setBookCover(int bookCover) {
        this.bookCover = bookCover;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bookCover);
        dest.writeString(bookTitle);
        dest.writeString(author);
        dest.writeString(writeDate);
        dest.writeString(editDate);
        dest.writeString(write);
        dest.writeFloat(ratingBar);
    }

    @Override
    public String toString() {
        return "Shelf_items{" +
                "id=" + id +
                ", bookCover=" + bookCover +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", writeDate='" + writeDate + '\'' +
                ", editDate='" + editDate + '\'' +
                ", write='" + write + '\'' +
                ", ratingBar=" + ratingBar +
                '}';
    }
}
