// Package persistence;
package persistence;

// Imports;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class ConnectionMySql;
public class ConnectionMySql {
    
    // Attributes;
    private final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private final String ADDRESS = String.format("jdbc:mysql://%s:%s/%s",
        Constants.IP, Constants.DOOR, Constants.NAME_DATABASE);

    // Connection Attribute;
    private static Connection connection;

    // Method to open a new connection with the Database;
    public void openConnection() {

        try {
            Class.forName(DRIVER_CLASS);
            connection = DriverManager.getConnection(ADDRESS, Constants.USER, Constants.PASSWORD);
        } 
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to close a new connection with the Database;
    public void closeConnection() {

        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter of the connection
    public static Connection getConnection() {
        return connection;
    }
}
