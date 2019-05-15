package app.literarium.parsingData.parseType;

public final class AuthorInfo {

    private final int id;
    private final String name;

    public AuthorInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
