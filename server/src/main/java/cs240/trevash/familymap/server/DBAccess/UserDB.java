package cs240.trevash.familymap.server.DBAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by trevash on 2/13/18.
 */

/**
 * Makes request to create user, verify userName is unused, check if userName and password match, fill User table
 */
public class UserDB {

    public UserDB() {}

    /**
     * This function will add a user to the User table and return a string object of that user
     * @param conn - Connection to the database
     * @param u - the sql statement to put the user into the database
     */
    public void addUser(Connection conn, String u) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(u);
        }
        catch (SQLException e) {
            throw new DatabaseException("Adding user failed", e);
        }
    }

    /**
     * When a user attempts to register, this checks to see if there is already a user present in the database
     * @param conn - Connection to the database
     * @param u - Stringified sql statement to verify user is not in DB
     * @return - returns a boolean, true if userName is not present, false if there already is one
     */
    public boolean userNameFree(Connection conn, String u) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(u);
            return !rs.next();
        }
        catch (SQLException e) {
            throw new DatabaseException("checking for free user failed", e);
        }
    }

    /**
     * Verifies that user is in table and that passwords match
     * @param conn - Connection to the database
     * @param u - Stringified sql statement to verify user is in DB
     * @return - Returns verified userName and personID if valid, null if not
     */
    public String userInTable(Connection conn, String u) throws DatabaseException {
        try {
            String personID = null;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(u);
            if (rs.next()) {
                personID = rs.getString(1);
            }
            return personID;
        }
        catch (SQLException e) {
            throw new DatabaseException("Checking for user failed", e);
        }
    }

    /**
     * Fills the database with the user objects provided by the FillDB class
     * @param conn - Connection to the database
     * @param us - Users to enter into the database
     */
    public void fillUser(Connection conn, String us) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(us);
        }
        catch (SQLException e) {
            throw new DatabaseException("Is valid auth check failed", e);
        }
    }
}
