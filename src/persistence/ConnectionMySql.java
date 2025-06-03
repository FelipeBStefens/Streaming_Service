// Package persistence;
package persistence;

// Imports;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class ConnectionMySql;
public class ConnectionMySql {

    private static final String IP = Constants.IP;
    private static final String BDNAME = Constants.NAME_DATABASE;
    private static final String PASSWORD = Constants.PASSWORD;
    private static final String DOOR = Constants.DOOR;
    private static final String LOGIN = Constants.USER;

    private static Connection connection;

    public static void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String endereco = "jdbc:mysql://"+IP +":"+DOOR +"/"+BDNAME;
            connection = DriverManager.getConnection(endereco, LOGIN, PASSWORD);
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if(!connection.isClosed()) {
                connection.close();
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
