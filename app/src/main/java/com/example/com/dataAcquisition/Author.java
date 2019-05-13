package literarium.parsingData;

public final class Author {

    private final String image_url, about, gender, homeTown, born_at, died_at;
    private final Integer fans_count, works_count;
    private final Book[] books;
    private final AuthorInfo info;

    public Author(Integer id, String name, Integer fans_count, String image_url, String about, Integer works_count, String gender, String homeTown, String born_at, String died_at, Book[] books) {
        this.info = new AuthorInfo(id, name);
        this.fans_count = fans_count;
        this.image_url = image_url;
        this.about = about;
        this.works_count = works_count;
        this.gender = gender;
        this.homeTown = homeTown;
        this.born_at = born_at;
        this.died_at = died_at;
        this.books = books;
    }

    public AuthorInfo getInfo() {
        return info;
    }

    public Integer getFans_count() {
        return fans_count;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getAbout() {
        return about;
    }

    public Integer getWorks_count() {
        return works_count;
    }

    public String getGender() {
        return gender;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public String getBorn_at() {
        return born_at;
    }

    public String getDied_at() {
        return died_at;
    }

    public Book[] getBooks() {
        return books;
    }

}
