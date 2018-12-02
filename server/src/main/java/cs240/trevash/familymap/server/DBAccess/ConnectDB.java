package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/15/18.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to establish a single connection with the database, so that each of the database
 * classes do not need to all open their own connection and also removes duplicate code
 */
public class ConnectDB {
    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection conn;
    private AuthTokenDB authTokenDB = new AuthTokenDB();
    private ClearDB clearDB = new ClearDB();
    private EventDB eventDB = new EventDB();
    private PersonDB personDB = new PersonDB();
    private UserDB userDB = new UserDB();

    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:spellcheck.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
    }

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public AuthTokenDB getAuthTokenDB() {
        return authTokenDB;
    }

    public ClearDB getClearDB() {
        return clearDB;
    }

    public EventDB getEventDB() {
        return eventDB;
    }

    public PersonDB getPersonDB() {
        return personDB;
    }

    public UserDB getUserDB() {
        return userDB;
    }
}
