package com.example.bookslog;

public class BestSellerItems {
   private String title;
   private String author;
   private String category;

    public BestSellerItems(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }
}
