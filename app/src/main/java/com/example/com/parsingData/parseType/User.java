package com.example.com.parsingData.parseType;

public final class User {

    private final String name, username, image_url, about, gender, interests;
    private final int age, friends_count, reviews_count;
    private final Shelf[] shelves;

    public User(String name, String username, String image_url, String about, int age, String gender, String interests, int friends_count, int reviews_count, Shelf[] shelves) {
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

    public int getAge() {
        return age;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public Shelf[] getShelves() {
        return shelves;
    }

    

}
