package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/27/18.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

import cs240.trevash.familymap.server.Services.Clear;

import static org.junit.Assert.assertTrue;

public class AuthTokenDBTest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;
    private ResultSet rs;
    private Clear clear = new Clear();

    public String insertStatement = "INSERT INTO AuthTokenTable (authToken, userName) VALUES ('myAuthToken', 'dName');";
    private String checkAuthStatement = "SELECT userName FROM AuthTokenTable WHERE authToken = 'myAuthToken';";

    @Before
    public void setUp() throws DatabaseException {
        db.openConnection();
        conn = db.getConn();
        db.getClearDB().clearDB(conn);
    }

    @After
    public void tearDown() throws DatabaseException {
        db.closeConnection(true);//Changes won't actually be added to the database
    }

    @Test
    public void testInsertOfAuthToken() throws DatabaseException {
            db.getAuthTokenDB().insertAuth(conn, insertStatement);
            String userName = db.getAuthTokenDB().isValidAuth(conn, checkAuthStatement);
            assertTrue(userName != null);
    }
}
