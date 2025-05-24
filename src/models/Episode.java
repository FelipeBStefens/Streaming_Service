// Package models;
package models;

// Imports;
import java.time.LocalDate;
import java.time.LocalTime;

// Class Episode
public class Episode {

    // Attributes of the class;
    private long idEpisode;
    private String title;
    private String description;
    private LocalTime duration;
    private LocalDate releaseDate;
    private Season season;
    
    // Constructor of the class;
    public Episode(long idEpisode, String title, String description, 
        LocalTime duration, LocalDate releaseDate) {

        // Declarations of the Attributes;
        this.idEpisode = idEpisode;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }
    
    // Getter and Setter of the id of the Episode;
    public long getIdEpisode() {
        return idEpisode;
    }

    public void setIdEpisode(long idEpisode) {
        this.idEpisode = idEpisode;
    }

    // Getter and Setter of the title of the Episode;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter of the description of the Episode;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter of the duration of the Episode;
    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    // Getter and Setter of the release date of the Episode;
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Getter and Setter of the Season date of the Episode;
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
