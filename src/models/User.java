// Package models;
package models;

// Imports;
import java.util.ArrayList;
import java.util.List;

// Class User;
public class User {
    
    // Attributes of the class;
    private long idUser;
    private String email;
    private String password;
    private String nickname;
    private String image;
    private final List<Rating> allRatings = new ArrayList<>();
    
    // Constructor of the class;
    public User(long idUser, String email, String password, String nickname, String image) {

        // Declarations of the attributes;
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }

    // Getter and Setter of the id of the User;
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    // Getter and Setter of the e-mail of the User;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter of the password of the User;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter of the nickname of the User;
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // Getter and Setter of the image of the User;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter of the List of all Ratings;
    public List<Rating> getAllRatings() {
        return allRatings;
    }

    // Method to add a Rating on the List;
    public void addRating(Rating rating) {

        allRatings.add(rating);
        rating.setUser(this);
    }

  
}