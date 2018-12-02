package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/13/18.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Makes calls to the AuthToken table to add a new auth, check if auth is valid, etc.
 */
public class AuthTokenDB {

    public AuthTokenDB() {}

    /**
     * This function inserts an authToken into the database, with the username associated with it.
     * @param insertString String from LoginHandler service to create a new user
     * @return Returns a successful login response
     */
    public void insertAuth(Connection conn, String insertString) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(insertString);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Insert Auth failed", e);
        }
    }

    /**
     * This function validates that a delivered authToken from a user is in the database
     * @param checkString - String containing the authToken and user that need to be validated
     * @return Returns true if authToken is in database and connected to user, false otherwise.
     */
    public String isValidAuth(Connection conn, String checkString) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkString);
            if (rs.next()) {
                return rs.getString(1);

            } else return null;
        }
        catch (SQLException e) {
            throw new DatabaseException("Is valid auth check failed", e);
        }
    }
}
