// Package models;
package models;

// Class Rating;
public class Rating {
    
    // Attributes of the class;
    private long idRating;
    private int rating;
    private User user;
    private Stream stream;

    // Constructor of the class;
    public Rating(long idRating, int rating) {

        // Declarations of the Attributses;
        this.idRating = idRating;
        this.rating = rating;
    }

    // Getter and Setter of the id of the Rating;
    public long getIdRating() {
        return idRating;
    }

    public void setIdRating(long idRating) {
        this.idRating = idRating;
    }

    // Getter and Setter of the rate of the Rating;
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Getter and Setter of the User of the Rating;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter of the Stream of the Rating;
    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    
}
