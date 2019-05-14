package com.example.com.dataAcquisition.parseType;

public final class Book {

    private final String title, isbn, image_url, publisher, description, amazon_buy_link;
    private final int id, publication_year, num_pages;
    private final double average_rating;
    private final AuthorInfo authors;

    public Book(int id, String title, String isbn, String image_url, int publication_year, String publisher, String description, String amazon_buy_link, double average_rating, int num_pages, AuthorInfo authors) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.image_url = image_url;
        this.publication_year = publication_year;
        this.publisher = publisher;
        this.description = description;
        this.amazon_buy_link = amazon_buy_link;
        this.average_rating = average_rating;
        this.num_pages = num_pages;
        this.authors = authors;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getImageUrl() {
        return image_url;
    }

    public int getPublicationYear() {
        return publication_year;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getAmazonBuyLink() {
        return amazon_buy_link;
    }

    public double getAverageRating() {
        return average_rating;
    }

    public int getNumPages() {
        return num_pages;
    }

    public AuthorInfo getAuthor() {
        return authors;
    }

}
