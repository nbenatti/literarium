public class User {
    
    // Attributes
    private String ID;
    private String name;
    private String imageURL;
    private int age;
    private String location;
    private String website;
    private List <String> interests;
    private int friendsCount;
    private HashMap <String, Integer> userShelves; // Shelf name and number of booitems per shelf
    
    // Costructor
    public User(String ID, String name, String imageURL, int age, String location, String website, List <String> interests, int friendsCount, HashMap <String, Integer> userShelves) {
        
        this.ID = ID;
        this.name = name;
        this.imageURL = imageURL;
        this.age = age;
        this.location = location;
        this.website = website;
        this.interests = interests;
        this.friendsCount = friendsCount;
        this.userShelves = userShelves;
        
    }
    
}