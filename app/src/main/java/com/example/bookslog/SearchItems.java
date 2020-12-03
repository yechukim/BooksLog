package com.example.bookslog;

public class SearchItems {
    private String title;
    private String author;
    private String price;
    private String imgUrl;

    public String getImageUrl() {
        return imgUrl;
    }

    public void setImageUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
