// Package models;
package models;

// Imports;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Class Serie;
public class Serie extends Stream{
    
    // Attribute of the class;
    private long idSerie;
    private final List<Season> allSeasons = new ArrayList<>();

    // Constructor of the class;
    public Serie(long idStream, String title, String description, 
        String poster, LocalDate releaseDate) {

        // Declarations of the attributes;
        super(title, description, poster, releaseDate);
        super.setIdStream(idStream);
    }

    // Getter and Setter of the id of the Serie;
    public long getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(long idSerie) {
        this.idSerie = idSerie;
    }

    // Getter of the List of all Seasons of the Serie;
    public List<Season> getAllSeasons() {
        return allSeasons;
    }

    // Method to add a Rating on the List;
    public void addSeason(Season season) {

        allSeasons.add(season);
        season.setSerie(this);
    }
}
