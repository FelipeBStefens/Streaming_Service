// Package models;
package models;

// Imports;
import java.time.LocalDate;
import java.time.LocalTime;

// Classe Movie;
public class Movie extends Stream{
    
    // Attributes of the class;
    private long idMovie;
    private LocalTime duration;

    // Constructor of the class;
    public Movie(long idMovie, LocalTime duration, long idStream, String title, 
        String description, String poster, LocalDate releaseDate) {

        // Declarations of the attributes;
        super(idStream, title, description, poster, releaseDate);
        this.idMovie = idMovie;
        this.duration = duration;
    }

    // Getter and Setter of the id of the Movie;
    public long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    // Getter and Setter of the duration of the Movie;
    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

}
