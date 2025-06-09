// Package dao inside the package persistence;
package persistence.dao;

// Imports;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import models.Rating;
import persistence.*;

// Class RatingDAO implementing the generic Interface referencing Rating; 
public class RatingDAO implements MethodsDAO<Rating>{

    // Sql code to insert a registrer value;
    private final String INSERT_SQL = 
        """
        INSERT INTO rating 
        VALUES 
        (NULL, ?, ?, ?);
        """;

    // Sql code to update a registrer value;
    private final String UPDATE_SQL = 
        """
        UPDATE rating
            SET rate = ?, id_stream = ?, id_user = ? 
            WHERE id_rating = ?;
        """;

    // Sql code to delete a registrer value;
    private final String DELETE_SQL =
        """
        DELETE FROM rating WHERE id_rating = ?;   
        """;

    // Sql code to select a registrer value by id;
    private final String SELECT_BY_ID_SQL = 
        """
        SELECT * FROM rating 
        WHERE id_rating = ?;   
        """;

    // Sql code to select all registrer values;
    private final String SELECT_ALL_SQL =
        """
        SELECT * FROM rating;    
        """;

    // Declaring a Prepared Statement and a Result Set;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // Overriding the insert method to the Database;
    @Override
    public void insert(Rating rating) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL);

            // Setting the values of that statement;
            preparedStatement.setInt(1, rating.getRating());
            preparedStatement.setLong(2, rating.getStream().getIdStream());
            preparedStatement.setLong(3, rating.getUser().getIdUser());

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
    public void update(Rating rating) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(UPDATE_SQL);

            // Setting the values of that statement;
            preparedStatement.setInt(1, rating.getRating());
            preparedStatement.setLong(2, rating.getStream().getIdStream());
            preparedStatement.setLong(3, rating.getUser().getIdUser());
            preparedStatement.setLong(4, rating.getIdRating());

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
    public void delete(Rating rating) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL);

            // Setting the values of that statement;
            preparedStatement.setLong(1, rating.getIdRating());

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
    public Rating searchById(long id) {

        Rating rating = null;

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
            if(resultSet.next()){
                
                // Defining the rating;
                rating = new Rating(resultSet.getInt("rate"));

                // Setting the the rating with the id;
                rating.setIdRating(resultSet.getLong("id_rating"));
                rating.getUser().setIdUser(resultSet.getLong("id_user"));
                rating.getStream().setIdStream(resultSet.getLong("id_stream"));
            }
        }
        catch(SQLException e) {

            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the rating;
        return rating;
    }

    // Overriding the search all method to the Database;
    @Override
    public List<Rating> searchAll() {
        
        List<Rating> listRating = new ArrayList<>();

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

                // Defining the rating;
                Rating rating = new Rating(resultSet.getInt("rate"));
                
                // Setting the the rating with the id;
                rating.setIdRating(resultSet.getLong("id_rating"));
                rating.getUser().setIdUser(resultSet.getLong("id_user"));
                rating.getStream().setIdStream(resultSet.getLong("id_stream"));

                // Adding the rating in the List;
                listRating.add(rating);
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
        return listRating;
    }
}
