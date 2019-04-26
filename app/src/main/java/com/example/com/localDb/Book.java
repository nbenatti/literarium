package com.example.com.localDb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "book")
public class Book {
    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Integer numPages;
    @NonNull
    private Boolean status;

    public Book(@NonNull Integer id, @NonNull String title, @NonNull String description, @NonNull Integer numPages, @NonNull Boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.numPages = numPages;
        this.status = status;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", numPages=" + numPages +
                ", status=" + status +
                '}';
    }
}
