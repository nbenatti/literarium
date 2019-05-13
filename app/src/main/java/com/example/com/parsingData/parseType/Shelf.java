package literarium.parsingData.parseType;

public final class Shelf {

    private final String name;
    private final int book_count;

    public Shelf(String name, int book_count) {
        this.name = name;
        this.book_count = book_count;
    }

    public String getName() {
        return name;
    }

    public int getBook_count() {
        return book_count;
    }

}
