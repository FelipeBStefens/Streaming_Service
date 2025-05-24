// Package models;
package models;

// Imports;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Class Stream;
public class Stream {
    
    // Attributes of the class;
    private long idStream;
    private String title;
    private String description;
    private String poster;
    private LocalDate releaseDate;
    private final List<Rating> allRatings = new ArrayList<>();

    // Constructor of the class;
    public Stream(long idStream, String title, String description, String poster, LocalDate releaseDate) {

        // Declarations of the attributes;
        this.idStream = idStream;
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.releaseDate = releaseDate;
    }

    // Getter and Setter of the id of the Stream;
    public long getIdStream() {
        return idStream;
    }

    public void setIdStream(long idStream) {
        this.idStream = idStream;
    }

    // Getter and Setter of the title of the Stream;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter of the description of the Stream;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter of the poster of the Stream;
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    // Getter and Setter of the release date of the Stream;
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Getter of the List of all Ratings of the Stream;
    public List<Rating> getAllRatings() {
        return allRatings;
    }

    // Method to add a Rating on the List;
    public void addRating(Rating rating) {

        allRatings.add(rating);
        rating.setStream(this);
    }

}
