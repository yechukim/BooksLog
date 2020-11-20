package com.example.bookslog;

public class Shelf_items {
    String bookTitle;
    String author;
    String details;

    public Shelf_items(String bookTitle, String author) {
        this.bookTitle = bookTitle;
        this.author = author;
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

    public String getDetails() {
        return details;
    }

    public void setDetails() {
        this.details = details;
    }
}
