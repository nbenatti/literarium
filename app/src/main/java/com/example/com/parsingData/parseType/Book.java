package literarium.parsingData.parseType;

public final class Book {

    private final String title, isbn, image_url, publisher, description;
    private final int id, publication_year, num_pages;
    private final double average_rating;
    private final AuthorInfo author;

    public Book(int id, String title, String isbn, String image_url, int publication_year, String publisher, String description, double average_rating, int num_pages, AuthorInfo author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.image_url = image_url;
        this.publication_year = publication_year;
        this.publisher = publisher;
        this.description = description;
        this.average_rating = average_rating;
        this.num_pages = num_pages;
        this.author = author;
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

    public String getImage_url() {
        return image_url;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public AuthorInfo getAuthors() {
        return author;
    }

}
