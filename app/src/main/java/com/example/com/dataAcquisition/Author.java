package com.example.com.dataAcquisition;

import java.util.List;

public class Author {

    private String name;
    private int fansCount;
    private String imageURL, smallImageURL, largeImageURL;
    String homeTown;
    int numBooks;
    private List<Book> books;

    public Author(String name, int fansCount, String imageURL, String smallImageURL, String largeImageURL, String homeTown, int numBooks) {
        this.name = name;
        this.fansCount = fansCount;
        this.imageURL = imageURL;
        this.smallImageURL = smallImageURL;
        this.largeImageURL = largeImageURL;
        this.homeTown = homeTown;
        this.numBooks = numBooks;
    }

    public void addBook(Book b) {
        books.add(b);
    }

    public String getName() {
        return name;
    }

    public int getFansCount() {
        return fansCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public int getNumBooks() {
        return numBooks;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", fansCount=" + fansCount +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }


}
