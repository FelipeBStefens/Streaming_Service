// Package dao inside the package persistence;
package persistence.dao;

// Imports;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.*;
import persistence.*;

// Class SerieDAO implementing the generic Interface referencing Serie; 
public class SerieDAO implements MethodsDAO<Serie>{

    // Sql code to insert a registrer value;
    private final String INSERT_SQL_STREAM = 
        """
        INSERT INTO streamTable 
        VALUES 
        (NULL, ?, ?, ?, ?);
        """;

    // Sql code to insert a registrer value;
    private final String INSERT_SQL_SERIE = 
        """
        INSERT INTO serie 
        VALUES 
        (?);
        """;

    // Sql code to select the max id of stream table;
    private final String SELECT_MAX_ID_STREAM = 
        """
        SELECT MAX(id_stream) FROM streamTable;    
        """;

    // Sql code to update a registrer value;
    private final String UPDATE_SQL = 
        """
        UPDATE stream
            SET title = ?, description_stream = ?, poster = ?, release_date = ? 
            WHERE id_stream = ?;
        """;

    // Sql code to delete a registrer value of stream;
    private final String DELETE_SQL_STREAM =
        """
        DELETE FROM serie WHERE id_stream = ?;   
        """;

    // Sql code to delete a registrer value of serie;
    private final String DELETE_SQL_SERIE =
        """
        DELETE FROM serie WHERE id_serie = ?;   
        """;

    // Sql code to select a registrer value by id;
    private final String SELECT_BY_ID_SQL = 
        """
        SELECT * FROM serie AS m INNER JOIN streamTable AS s ON m.id_serie = s.id_stream 
        WHERE id_serie=?;
        """;

    // Sql code to select all registrer values;
    private final String SELECT_ALL_SQL =
        """
        SELECT * FROM serie AS m INNER JOIN streamTable AS s ON m.id_serie = s.id_stream;   
        """;

    // Declaring a Prepared Statement and a Result Set;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // Overriding the insert method to the Database;
    @Override
    public void insert(Serie serie) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL_STREAM);
            
            // Setting the values of that statement;
            preparedStatement.setString(1, serie.getTitle());
            preparedStatement.setString(2, serie.getDescription());
            preparedStatement.setString(3, serie.getPoster());
            preparedStatement.setDate(4, Date.valueOf(serie.getReleaseDate()));

            // Executing the statement;
            preparedStatement.executeUpdate();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(SELECT_MAX_ID_STREAM);

            // Executing the query;
            resultSet = preparedStatement.executeQuery();

            // If there is one register in the query;
            long idStream = 0;
            if(resultSet.next()) {

                // Getting the values of that statement;
                idStream = resultSet.getLong(1);
            }

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL_SERIE);

            // Setting the values of that statement;
            preparedStatement.setLong(1, idStream);

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
    public void update(Serie serie) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(UPDATE_SQL);

            // Setting the values of that statement;
            preparedStatement.setString(1, serie.getTitle());
            preparedStatement.setString(2, serie.getDescription());
            preparedStatement.setString(3, serie.getPoster());
            preparedStatement.setDate(4, Date.valueOf(serie.getReleaseDate()));
            preparedStatement.setLong(5,serie.getIdStream());

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
    public void delete(Serie serie) {
        
        // Try-Catch-Finally to find Exceptions;
        try{

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL_STREAM);
            
            // Setting the values of that statement;
            preparedStatement.setLong(1,serie.getIdStream());
            
            // Executing the statement;
            preparedStatement.executeUpdate();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL_SERIE);

            // Setting the values of that statement;
            preparedStatement.setLong(1,serie.getIdSerie());
            
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
    public Serie searchById(long id) {
        
        Serie serie = null;

        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(SELECT_BY_ID_SQL);

            // Setting the values of that statement;
            preparedStatement.setLong(1, id);

            // Executing the query;
            resultSet= preparedStatement.executeQuery();

            // If there is one register in the query;
            if(resultSet.next()) {

                // Defining the serie;
                serie = new Serie(resultSet.getLong("id_stream"), 
                    resultSet.getString("title"),
                    resultSet.getString("description_stream"), 
                    resultSet.getString("poster"),
                    resultSet.getDate("release_date").toLocalDate());

                // Setting the the serie with the id;
                serie.setIdSerie(resultSet.getLong("id_serie"));
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
        return serie;
    }

    // Overriding the search all method to the Database;
    @Override
    public List<Serie> searchAll() {
        
        List<Serie> listSeries = new ArrayList<>();

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

                // Defining the serie;
                Serie serie = new Serie(resultSet.getLong("id_stream"), 
                    resultSet.getString("title"),
                    resultSet.getString("description_stream"), 
                    resultSet.getString("poster"),
                    resultSet.getDate("release_date").toLocalDate());

                // Setting the the serie with the id;
                serie.setIdSerie(resultSet.getLong("id_serie"));
                
                // Adding the serie in the List;
                listSeries.add(serie);
            }
        }
        catch (SQLException e) {

            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the list of episode;
        return listSeries;
    }
}