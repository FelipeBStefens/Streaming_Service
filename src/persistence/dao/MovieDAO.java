// Package dao inside the package persistence;
package persistence.dao;

// Imports;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Movie;
import persistence.*;

// Class MovieDAO implementing the generic Interface referencing Movie;
public class MovieDAO implements MethodsDAO<Movie>{

     // Sql code to insert a registrer value;
    private final String INSERT_SQL_STREAM = 
        """
        INSERT INTO streamTable 
        VALUES 
        (NULL, ?, ?, ?, ?);
        """;

    // Sql code to insert a registrer value;
    private final String INSERT_SQL_MOVIE = 
        """
        INSERT INTO movie 
        VALUES 
        (?, ?);
        """;

    // Sql code to select the max id of stream table;
    private final String SELECT_MAX_ID_STREAM = 
        """
        SELECT MAX(id_stream) FROM streamTable;    
        """;

    // Sql code to update a registrer value;
    private final String UPDATE_SQL_STREAM = 
        """
        UPDATE stream
            SET title = ?, description_stream = ?, poster = ?, release_date = ? 
            WHERE id_stream = ?;
        """;

    // Sql code to update a registrer value;
    private final String UPDATE_SQL_MOVIE = 
        """
        UPDATE movie
            SET duration = ? 
            WHERE id_movie = ?;
        """;

    // Sql code to delete a registrer value of stream;
    private final String DELETE_SQL_STREAM =
        """
        DELETE FROM serie WHERE id_stream = ?;   
        """;

    // Sql code to delete a registrer value of serie;
    private final String DELETE_SQL_MOVIE =
        """
        DELETE FROM movie WHERE id_movie = ?;   
        """;

    // Sql code to select a registrer value by id;
    private final String SELECT_BY_ID_SQL = 
        """
        SELECT * FROM movie AS m INNER JOIN streamTable AS s ON m.id_movie = s.id_stream 
        WHERE id_movie=?;
        """;

    // Sql code to select all registrer values;
    private final String SELECT_ALL_SQL =
        """
        SELECT * FROM movie AS m INNER JOIN streamTable AS s ON m.id_movie = s.id_stream;   
        """;

    // Declaring a Prepared Statement and a Result Set;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // Overriding the insert method to the Database;
    @Override
    public void insert(Movie movie) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL_STREAM);
            
            // Setting the values of that statement;
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDescription());
            preparedStatement.setString(3, movie.getPoster());
            preparedStatement.setDate(4, Date.valueOf(movie.getReleaseDate()));

            // Executing the statement;
            preparedStatement.executeUpdate();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(SELECT_MAX_ID_STREAM);

            // Executing the query;
            resultSet = preparedStatement.executeQuery();

            // If there is one register in the query;
            long idStream = 0;
            if(resultSet.next()) {
                idStream = resultSet.getLong(1);
            }

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL_MOVIE);

            // Setting the values of that statement;
            preparedStatement.setLong(1, idStream);
            preparedStatement.setTime(2, Time.valueOf(movie.getDuration()));

            // Executing the statement;
            preparedStatement.executeUpdate();

        }catch(SQLException e) {

            System.out.println("Error inserting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }
    }

    // Overriding the update method to the Database;
    @Override
    public void update(Movie movie) {

        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(UPDATE_SQL_STREAM);
        
            // Setting the values of that statement;
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDescription());
            preparedStatement.setString(3, movie.getPoster());
            preparedStatement.setDate(4, Date.valueOf(movie.getReleaseDate()));
            preparedStatement.setLong(5,movie.getIdStream());

            // Executing the statement;
            preparedStatement.executeUpdate();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(UPDATE_SQL_MOVIE);

            // Setting the values of that statement;
            preparedStatement.setTime(1, Time.valueOf(movie.getDuration()));
            preparedStatement.setLong(2,movie.getIdMovie());

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
    public void delete(Movie movie) {
       
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL_STREAM);
            
            // Setting the values of that statement;
            preparedStatement.setLong(1,movie.getIdStream());

            // Executing the statement;
            preparedStatement.executeUpdate();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL_MOVIE);

            // Setting the values of that statement;
            preparedStatement.setLong(1,movie.getIdMovie());

            // Executing the statement;
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {

            System.out.println("Error deleting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }
    }

    // Overriding the search by id method to the Database;
    @Override
    public Movie searchById(long id) {

        Movie movie = null;

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

                // Defining the movie;
                movie = new Movie(resultSet.getTime("duration").toLocalTime(),
                        resultSet.getLong("id_stream"), 
                        resultSet.getString("title"),
                        resultSet.getString("description_stream"), 
                        resultSet.getString("poster"),
                        resultSet.getDate("release_date").toLocalDate());

                // Setting the the movie with the id;
                movie.setIdMovie(resultSet.getLong("id_movie"));
            }
        }
        catch (SQLException e) {

            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the movie;
        return movie;
    }

    // Overriding the search all method to the Database;
    @Override
    public List<Movie> searchAll() {
        
        List<Movie> listMovies = new ArrayList<>();

        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(SELECT_ALL_SQL);

            // Executing the query;
            resultSet = preparedStatement.executeQuery();

            // While there is one register in the query;
            while(resultSet.next()) {

                // Defining the movie;
                Movie movie = new Movie(resultSet.getTime("duration").toLocalTime(),
                        resultSet.getLong("id_stream"), resultSet.getString("title"),
                        resultSet.getString("description_stream"), resultSet.getString("poster"),
                        resultSet.getDate("release_date").toLocalDate());

                // Setting the the movie with the id;
                movie.setIdMovie(resultSet.getLong("id_movie"));
                
                // Adding the movie in the List;
                listMovies.add(movie);
            }
        }
        catch (SQLException e) {
            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }
        
        // Returning the list of movies;
        return listMovies;
    }
}
