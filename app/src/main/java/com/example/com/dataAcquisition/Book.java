package com.example.com.literarium;

import android.os.Parcelable;

public class Book {

    private String title;
    private String isbn;
    private String imageURL, smallImageURL, largeImageURL;
    private int publicationYear;
    private String publisher;
    private String description;
    private String amazonBuyLink;
    private int numPages;
    private String author;

    public Book(String title, String isbn,
                String imageURL, String smallImageURL, String largeImageURL,
                int publicationYear, String publisher, String description,
                String amazonBuyLink, int numPages, String author) {
        this.title = title;
        this.isbn = isbn;
        this.imageURL = imageURL;
        this.smallImageURL = smallImageURL;
        this.largeImageURL = largeImageURL;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.description = description;
        this.amazonBuyLink = amazonBuyLink;
        this.numPages = numPages;
        this.author = author;
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

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
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
