package com.example.com.dataAcquisition;

import android.os.Parcelable;

public class Book {

    private int id;
    private String title;
    private String isbn;
    private String imageURL;
    private int publicationYear;
    private String publisher;
    private String description;
    private String amazonBuyLink;
    private int numPages;
    private String author;

    public Book(int id, String title, String isbn,
                String imageURL,
                int publicationYear, String publisher, String description,
                String amazonBuyLink, int numPages, String author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.imageURL = imageURL;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.description = description;
        this.amazonBuyLink = amazonBuyLink;
        this.numPages = numPages;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getAmazonBuyLink() {
        return amazonBuyLink;
    }

    public int getNumPages() {
        return numPages;
    }
}
