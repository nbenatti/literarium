package literarium.localDB;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "book", primaryKeys = {"bookId", "userId"})
public class Book {

    @NonNull
    private int bookId, publication_year, num_pages, id_author;
    @NonNull
    private String userId, title, isbn, image_url, publisher, description, amazon_buy_link, name_author;
    @NonNull
    private boolean status, seen;
    @NonNull
    private double average_rating;

    public Book(int bookId, String userId, String title, String isbn, String image_url, int publication_year, String publisher, String description, String amazon_buy_link, double average_rating, int num_pages, int id_author, String name_author) {
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.isbn = isbn;
        this.image_url = image_url;
        this.publication_year = publication_year;
        this.publisher = publisher;
        this.description = description;
        this.amazon_buy_link = amazon_buy_link;
        this.average_rating = average_rating;
        this.num_pages = num_pages;
        this.id_author = id_author;
        this.name_author = name_author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
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

    public String getAmazon_buy_link() {
        return amazon_buy_link;
    }

    public void setAmazon_buy_link(String amazon_buy_link) {
        this.amazon_buy_link = amazon_buy_link;
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

}
