// Package dao inside the package persistence;
package persistence.dao;

// Imports;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import models.Episode;
import persistence.*;

// Class EpisodeDAO implementing the generic Interface referencing Episode; 
public class EpisodeDAO implements MethodsDAO<Episode>{

    // Sql code to insert a registrer value;
    private final String INSERT_SQL = 
        """
        INSERT INTO episode 
        VALUES 
        (NULL, ?, ?, ?, ?, ?);
        """;
    
    // Sql code to update a registrer value;
    private final String UPDATE_SQL = 
        """
        UPDATE episode
            SET title = ?, description_stream = ?, release_date = ?, duration = ?, id_season = ? 
            WHERE id_episode = ?;
        """;
    
    // Sql code to delete a registrer value;
    private final String DELETE_SQL =
        """
        DELETE FROM episode WHERE id_episode=?;   
        """;

    // Sql code to select a registrer value by id;
    private final String SELECT_BY_ID_SQL = 
        """
        SELECT * FROM episode 
        WHERE id_episode=?;   
        """;

    // Sql code to select all registrer values;
    private final String SELECT_ALL_SQL =
        """
        SELECT * FROM episode;    
        """;

    // Declaring a Prepared Statement and a Result Set;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // Overriding the insert method to the Database;
    @Override
    public void insert(Episode episode) {

        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL);

            // Setting the values of that statement;
            preparedStatement.setString(1, episode.getTitle());
            preparedStatement.setString(2, episode.getDescription());
            preparedStatement.setDate(3, Date.valueOf(episode.getReleaseDate()));
            preparedStatement.setTime(4, Time.valueOf(episode.getDuration()));
            preparedStatement.setLong(5, episode.getSeason().getIdSeason());

            // Executing the statement;
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            
            System.out.println("Error inserting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }
    }

    // Overriding the update method to the Database;
    @Override
    public void update(Episode episode) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(UPDATE_SQL);

            // Setting the values of that statement;
            preparedStatement.setString(1, episode.getTitle());
            preparedStatement.setString(2, episode.getDescription());
            preparedStatement.setDate(3, Date.valueOf(episode.getReleaseDate()));
            preparedStatement.setTime(4, Time.valueOf(episode.getDuration()));
            preparedStatement.setLong(5, episode.getSeason().getIdSeason());
            preparedStatement.setLong(6, episode.getIdEpisode());

            // Executing the statement;
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {

            System.out.println("Error updating a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }
    }

    // Overriding the delete method to the Database;
    @Override
    public void delete(Episode episode) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL);

            // Setting the values of that statement;
            preparedStatement.setLong(1, episode.getIdEpisode());

            // Executing the statement;
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {

            System.out.println("Error deleting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }
    }

    // Overriding the search by id method to the Database;
    @Override
    public Episode searchById(long id) {
        
        Episode episode = null;

        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(SELECT_BY_ID_SQL);

            // Setting the values of that statement;
            preparedStatement.setLong(1, id);

            // Executing the query;
            resultSet = preparedStatement.executeQuery();

            // If there is one register in the query;
            if(resultSet.next()) {

                // Defining the episode;
                episode = new Episode(resultSet.getString("title"), 
                    resultSet.getString("description_stream"), 
                    resultSet.getTime("duration").toLocalTime(),
                    resultSet.getDate("release_date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                
                // Setting the the episode with the id;
                episode.setIdEpisode(resultSet.getLong("id_episode"));
            }
        }
        catch (SQLException e) {
            
            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the episode;
        return episode;
    }

    // Overriding the search all method to the Database;
    @Override
    public List<Episode> searchAll() {

        List<Episode> listEpisode = new ArrayList<>();
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(SELECT_ALL_SQL);

            // Executing the query;
            resultSet = preparedStatement.executeQuery();

            // While there is one register in the query;
            while (resultSet.next()) {

                // Defining the episode;
                Episode episode = new Episode(resultSet.getString("title"), 
                    resultSet.getString("description_stream"),
                    resultSet.getTime("duration").toLocalTime(),
                    resultSet.getDate("release_date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                
                // Setting the the episode with the id;
                episode.setIdEpisode(resultSet.getLong("id_episode"));

                // Adding the episode in the List;
                listEpisode.add(episode);
            }
        }
        catch(SQLException e) {

            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the list of episode;
        return listEpisode;
    }
}