package com.example.com.parsingData.parseType;

import android.os.Parcel;
import android.os.Parcelable;

public final class Book implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(title);
        out.writeString(isbn);
        out.writeString(image_url);
        out.writeString(publisher);
        out.writeString(description);
        out.writeString(publication_year);
        out.writeString(num_pages);
        out.writeInt(id);
        out.writeDouble(average_rating);
        out.writeParcelable(authors, i);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel p) {

        title = p.readString();
        isbn = p.readString();
        image_url = p.readString();
        publisher = p.readString();
        description = p.readString();
        publication_year = p.readString();
        num_pages = p.readString();
        id = p.readInt();
        average_rating = p.readDouble();
        authors = p.readParcelable(AuthorInfo.class.getClassLoader());
    }
}
