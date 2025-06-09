// Package dao inside the package persistence;
package persistence.dao;

// Imports;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.User;
import persistence.*;

// Class UserDAO implementing the generic Interface referencing User;
public class UserDAO implements MethodsDAO<User>{

    // Sql code to insert a registrer value;
    private final String INSERT_SQL = 
        """
        INSERT INTO user 
        VALUES 
        (NULL, ?, ?, ?, ?);
        """;

    // Sql code to update a registrer value;
    private final String UPDATE_SQL = 
        """
        UPDATE user 
            SET nickname = ?, email = ?, password_user = ?, image_user = ? 
            WHERE id_user = ?;
        """;

    // Sql code to delete a registrer value;
    private final String DELETE_SQL =
        """
        DELETE FROM user WHERE id_user = ?;   
        """;

    // Sql code to select a registrer value by id;
    private final String SELECT_BY_ID_SQL = 
        """
        SELECT * FROM user 
        WHERE id_user = ?;  
        """;

    // Sql code to select all registrer values;
    private final String SELECT_ALL_SQL =
        """
        SELECT * FROM user;    
        """;

    // Declaring a Prepared Statement and a Result Set;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    // Overriding the insert method to the Database;
    @Override
    public void insert(User user) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(INSERT_SQL);

            // Setting the values of that statement;
            preparedStatement.setString(1, user.getNickname());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getImage());

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
    public void update(User user) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(UPDATE_SQL);

            // Setting the values of that statement;
            preparedStatement.setString(1, user.getNickname());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getImage());
            preparedStatement.setLong(5, user.getIdUser());

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
    public void delete(User user) {
        
        // Try-Catch-Finally to find Exceptions;
        try {

            // Open a connection with the Database;
            ConnectionMySql.openConnection();

            // Prepared a new Statement;
            preparedStatement = ConnectionMySql.getConnection().prepareStatement(DELETE_SQL);

            // Setting the values of that statement;
            preparedStatement.setLong(1, user.getIdUser());

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
    public User searchById(long id) {
        
        User user = null;

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

                // Defining the user;
                user = new User(resultSet.getString("email"),
                        resultSet.getString("password_user"),
                        resultSet.getString("nickname"),
                        resultSet.getString("image_user"));

                // Setting the the user with the id;
                user.setIdUser(resultSet.getLong("id_user"));
            }
        }
        catch (SQLException e) {
            
            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the user;
        return user;
    }

    // Overriding the search all method to the Database;
    @Override
    public List<User> searchAll() {

        List<User> listUser = new ArrayList<>();

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

                // Defining the user;
                User user = new User(resultSet.getString("email"),
                        resultSet.getString("password_user"),
                        resultSet.getString("nickname"),
                        resultSet.getString("image_user"));

                // Setting the the user with the id;
                user.setIdUser(resultSet.getLong("id_user"));

                // Adding the episode in the List;
                listUser.add(user);
            }
        }
        catch (SQLException e) {
            
            System.out.println("Error selecting a Statement on the Database");
        }
        finally {

            // Close the connections with the Database;
            ConnectionMySql.closeConnection();
        }

        // Returning the list of user;
        return listUser;
    }
}