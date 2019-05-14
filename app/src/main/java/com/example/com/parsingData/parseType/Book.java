package com.example.com.parsingData.parseType;

public final class Book {

    private final String title, isbn, image_url, publisher, description, publication_year, num_pages;
    private final int id;
    private final double average_rating;
    private final com.example.com.parsingData.parseType.AuthorInfo authors;

    public Book(int id, String title, String isbn, String image_url, String publication_year, String publisher, String description, double average_rating, String num_pages, AuthorInfo authors) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.image_url = image_url;
        this.publication_year = publication_year;
        this.publisher = publisher;
        this.description = description;
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

    public String getPublicationYear() {
        return publication_year;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageRating() {
        return average_rating;
    }

    public String getNumPages() {
        return num_pages;
    }

    public AuthorInfo getAuthor() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", image_url='" + image_url + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", publication_year='" + publication_year + '\'' +
                ", num_pages='" + num_pages + '\'' +
                ", id=" + id +
                ", average_rating=" + average_rating +
                ", authors=" + authors +
                '}';
    }
}
