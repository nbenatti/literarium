package literarium.parsingData;

public final class Book {

    private final String title, isbn, image_url, publisher, description, amazon_buy_link;
    private final Integer id, publication_year, num_pages;
    private final Double average_rating;
    private final AuthorInfo[] authors;

    public Book(Integer id, String title, String isbn, String image_url, Integer publication_year, String publisher, String description, String amazon_buy_link, Double average_rating, Integer num_pages, AuthorInfo[] authors) {
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

    public Integer getId() {
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

    public Integer getPublication_year() {
        return publication_year;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getAmazon_buy_link() {
        return amazon_buy_link;
    }

    public Double getAverage_rating() {
        return average_rating;
    }

    public Integer getNum_pages() {
        return num_pages;
    }

    public AuthorInfo[] getAuthors() {
        return authors;
    }

}
