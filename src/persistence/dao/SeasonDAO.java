// Package dao inside the package persistence;
package persistence.dao;

// Imports;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Season;
import persistence.*;

// Class SeasonDAO implementing the generic Interface referencing Season;
public class SeasonDAO implements MethodsDAO<Season>{

    // Sql code to insert a registrer value;
    private final String INSERT_SQL = 
        """
        INSERT INTO season 
        VALUES 
        (NULL, ?, ?);
        """;

    // Sql code to update a registrer value;
    private final String UPDATE_SQL = 
        """
        UPDATE season
            SET SET number_season = ? 
            WHERE id_season = ?;
        """;

    // Sql code to delete a registrer value;
    private final String DELETE_SQL =
        """
        DELETE FROM season WHERE id_season=?;;   
        """;

    // Sql code to select a registrer value by id;
    private final String SELECT_BY_ID_SQL = 
        """
        SELECT * FROM season 
        WHERE id_season=?;
        """;

    // Sql code to select all registrer values;
    private final String SELECT_ALL_SQL =
        """
        SELECT * FROM season;    
        """;

    // Declaring a Prepared Statement and a Result Set;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // Overriding the insert method to the Database;
    @Override
    public void insert(Season season) {

        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL);

            // Setting the values of that statement;
            preparedStatement.setInt(1, season.getNumber());
            preparedStatement.setLong(2, season.getSerie().getIdSerie());

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
    public void update(Season season) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(UPDATE_SQL);

            // Setting the values of that statement;
            preparedStatement.setInt(1, season.getNumber());
            preparedStatement.setLong(2, season.getIdSeason());

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
    public void delete(Season season) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL);

            // Setting the values of that statement;
            preparedStatement.setLong(1, season.getIdSeason());

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
    public Season searchById(long id) {
        
        Season season = null;

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

                // Defining the season;
                season = new Season(resultSet.getInt("number_season"));

                // Setting the the season with the id;
                season.setIdSeason(resultSet.getLong("id_season"));
            }
        }
        catch(SQLException e) {

            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the season;
        return season;
    }

    // Overriding the search all method to the Database;
    @Override
    public List<Season> searchAll() {
        
        List<Season> listSeason = new ArrayList<>();

        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(SELECT_ALL_SQL);

            // Executing the query;
            resultSet = preparedStatement.executeQuery();

            // While there is one register in the query;
            while(resultSet.next()){
                
                // Defining the season;
                Season season = new Season(resultSet.getInt("number_season"));
                
                // Setting the the season with the id;
                season.setIdSeason(resultSet.getLong("id_season"));

                // Adding the episode in the List;
                listSeason.add(season);
            }
        }
        catch(SQLException e) {

             System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the list of  season;
        return listSeason;
    }
}
