package Database;

import java.sql.*;
import java.util.logging.Logger;

/**
    <p>This class is used to connect the classes to the database</p>
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String Driver = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/smarthouse";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static ConnectionFactory singleInstance=new ConnectionFactory();

    private ConnectionFactory() {
        try{
            Class.forName(Driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Connection createConnection()  {
        Connection connection=null;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * <p>Is used to create a connection to the database</p>
     * @return the connection to the database
     */
    public static Connection getConnection() throws SQLException {
        return singleInstance.createConnection();
    }

    /**
     * Closes the connection to the database that is given
     * @param connection is the connection to the database
     * @throws SQLException
     */
    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
    /**
     * Closes the statement that is given
     * @param statement is the statement that is closed
     * @throws SQLException
     */
    public static void closeStatement(Statement statement) throws SQLException {
        statement.close();
    }
    /**
     * Closes the resultSet that is given
     * @param resultSet is the statement that is closed
     * @throws SQLException
     */
    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }
}
