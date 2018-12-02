package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/14/18.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to clean out the database
 */
public class ClearDB {

    /**
     * Completely wipes the database and removes all rows in all tables
     */
    public void clearDB(Connection conn) throws DatabaseException {

        try {
            String removeAuths = "DELETE FROM AuthTokenTable;";
            String removeEvents = "DELETE FROM EventTable";
            String removePersons = "DELETE FROM PersonTable";
            String removeUsers = "DELETE FROM UserTable";

            Statement stmt = conn.createStatement();
            stmt.execute(removeAuths);
            stmt.execute(removeEvents);
            stmt.execute(removePersons);
            stmt.execute(removeUsers);
        }
        catch (SQLException e) {
            throw new DatabaseException("Clearing the database failed", e);
        }

    }

    public boolean isEmpty(Connection conn) throws DatabaseException {
        try {
            String checkAuth = "SELECT * FROM AuthTokenTable WHERE authToken = 'myAuthToken';";
            String checkEvents = "SELECT * FROM EventTable";
            String checkPersons = "SELECT * FROM PersonTable";
            String checkUsers = "SELECT * FROM UserTable";

            Statement stmt = conn.createStatement();
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            Statement stmt3 = conn.createStatement();

            ResultSet rs = stmt.executeQuery(checkAuth);
            ResultSet rs1 = stmt1.executeQuery(checkEvents);
            ResultSet rs2 = stmt2.executeQuery(checkPersons);
            ResultSet rs3 = stmt3.executeQuery(checkUsers);

            return (!rs.next() && !rs1.next() && !rs2.next() && !rs3.next());
        }
        catch (SQLException e) {
            throw new DatabaseException("checking if database is empty failed", e);
        }
    }

    public void clearForUser(Connection conn, String userName) throws DatabaseException {
        try {
            String removeEvents = "DELETE FROM EventTable WHERE descendant = '" + userName + "';";
            String removePersons = "DELETE FROM PersonTable WHERE descendant = '" + userName + "';";

            Statement stmt = conn.createStatement();
            stmt.execute(removeEvents);
            stmt.execute(removePersons);
        }
        catch (SQLException e) {
            throw new DatabaseException("clearing values for specific user failed", e);
        }
    }
}
