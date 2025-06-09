// Package persistence;
package persistence;

// Imports;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class ConnectionMySql;
public class ConnectionMySql {

    // Connection with the Database in a property;
    private static Connection connection;

    // Address of the Database;
    private static final String ADDRESS = "jdbc:mysql://" + Constants.IP + ":" + Constants.DOOR + "/" + 
        Constants.NAME_DATABASE;

    // Method to open the connection with the Database;
    public static void openConnection() {

        // Try-Catch to find Exceptions;
        try {

            // Opening the connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(ADDRESS, Constants.USER, Constants.PASSWORD);
        }
        catch(ClassNotFoundException | SQLException e) {

            System.out.println("Error opening the connection to the Database");
        }
    }

    // Method to close the connection with the Database;
    public static void closeConnection() {

        // Try-Catch to find Exceptions;
        try {

            // Closing the connection;
            if(!connection.isClosed()) {
                connection.close();
            }
        }
        catch(SQLException e) {
            
            System.out.println("Error closing the connection to the Database");
        }
    }

    // Getter of the property connection;
    public static Connection getConnection() {
        return connection;
    }
}
