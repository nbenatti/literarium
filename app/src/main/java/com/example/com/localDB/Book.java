package com.example.com.localDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "book", primaryKeys = {"bookId", "userId"})
public class Book {
    @NonNull
    private Integer bookId;
    @NonNull
    private Integer userId;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Integer numPages;
    @NonNull
    private Boolean status;

    public Book(@NonNull Integer bookId, @NonNull Integer userId, @NonNull String title, @NonNull String description, @NonNull Integer numPages, @NonNull Boolean status) {
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.numPages = numPages;
        this.status = status;
    }

    @NonNull
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(@NonNull Integer bookId) {
        this.bookId = bookId;
    }

    @NonNull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(@NonNull Integer numPages) {
        this.numPages = numPages;
    }

    @NonNull
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(@NonNull Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", numPages=" + numPages +
                ", status=" + status +
                '}';
    }
}
