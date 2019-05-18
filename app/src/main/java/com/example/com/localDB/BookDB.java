package com.example.com.localDB;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "books", primaryKeys = {"bookId", "userId"})
public class BookDB {

    @NonNull
    private int bookId, id_author;
    @NonNull
    private String userId, title, isbn, image_url, publisher, description, name_author, publication_year, num_pages;
    @NonNull
    private boolean status, seen;
    @NonNull
    private double average_rating;

    public BookDB(int bookId, String userId, String title, String isbn, String image_url, String publication_year, String publisher, String description, double average_rating, String num_pages, int id_author, String name_author, boolean seen, boolean status) {
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.isbn = isbn;
        this.image_url = image_url;
        this.publication_year = publication_year;
        this.publisher = publisher;
        this.description = description;
        this.average_rating = average_rating;
        this.num_pages = num_pages;
        this.id_author = id_author;
        this.name_author = name_author;
        this.status = status;
        this.seen = seen;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(String publication_year) {
        this.publication_year = publication_year;
    }

    public String getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(String num_pages) {
        this.num_pages = num_pages;
    }

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_author() {
        return name_author;
    }

    public void setName_author(String name_author) {
        this.name_author = name_author;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }

    @Override
    public String toString() {
        return "BookDB{" +
                "bookId=" + bookId +
                ", id_author=" + id_author +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", image_url='" + image_url + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", name_author='" + name_author + '\'' +
                ", publication_year='" + publication_year + '\'' +
                ", num_pages='" + num_pages + '\'' +
                ", status=" + status +
                ", seen=" + seen +
                ", average_rating=" + average_rating +
                '}';
    }
}
