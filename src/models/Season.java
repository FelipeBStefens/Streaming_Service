// Package models;
package models;

// Imports;
import java.util.ArrayList;
import java.util.List;

// Class Season;
public class Season {
    
    // Attributes of the class;
    private long idSeason;
    private int number;
    private Serie serie;
    private final List<Episode> allEpisodes = new ArrayList<>();

    // Constructor of the class;
    public Season(int number) {

        // Declarations of the attributes;
        this.number = number;
    }

    // Getter and Setter of the id of the Season;
    public long getIdSeason() {
        return idSeason;
    }

    public void setIdSeason(long idSeason) {
        this.idSeason = idSeason;
    }

    // Getter and Setter of the number of the Season;
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    // Getter and Setter of the Serie of the Season;
    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
        serie.getAllSeasons().add(this);
    }

    // Getter of the List of all the Episodes of the Season;
    public List<Episode> getAllEpisodes() {
        return allEpisodes;
    }

    // Method to add a Episode on the List;
    public void addEpisode(Episode episode) {

        allEpisodes.add(episode);
        episode.setSeason(this);
    }
}
