package literarium.parsingData;

public final class User {

    private final String name, username, image_url, about, gender, interests;
    private final Integer age, friends_count, reviews_count;
    private final Shelf[] shelves;

    public User(String name, String username, String image_url, String about, Integer age, String gender, String interests, Integer friends_count, Integer reviews_count, Shelf[] shelves) {
        this.name = name;
        this.username = username;
        this.image_url = image_url;
        this.about = about;
        this.age = age;
        this.gender = gender;
        this.interests = interests;
        this.friends_count = friends_count;
        this.reviews_count = reviews_count;
        this.shelves = shelves;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getAbout() {
        return about;
    }

    public String getGender() {
        return gender;
    }

    public String getInterests() {
        return interests;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getFriends_count() {
        return friends_count;
    }

    public Integer getReviews_count() {
        return reviews_count;
    }

    public Shelf[] getShelves() {
        return shelves;
    }

    

}
