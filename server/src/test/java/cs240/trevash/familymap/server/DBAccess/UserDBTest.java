package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/27/18.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDBTest {

    private ConnectDB db = new ConnectDB();
    private Connection conn;

    public String addUserStatement = "INSERT INTO UserTable (userName, password, email, firstName, lastName, gender, personID) VALUES ('uName', 'password', 'mail@mail.com', 'FN', 'LN', 'M','id');";
    public String userNameFreeStatement = "SELECT * FROM UserTable WHERE userName = 'uName';";
    public String userInTable = "SELECT * FROM UserTable WHERE userName = 'uName' AND password = 'password';";
    public String userInTable1 = "SELECT * FROM UserTable WHERE userName = 'uName1' AND password = 'password';";
    public String fillUserStatement = "INSERT INTO UserTable (userName, password, email, firstName, lastName, gender, personID) VALUES ('uName', 'password', 'mail@mail.com', 'FN', 'LN', 'M','id'), ('uName1', 'password', 'mail@mail.com', 'FN', 'LN', 'M','id1');";

    @Before
    public void setUp() throws DatabaseException {
        db.openConnection();
        conn = db.getConn();
        db.getClearDB().clearDB(conn);
    }

    @After
    public void tearDown() throws DatabaseException {
        db.closeConnection(false);
    }

    @Test
    public void addUserUserInTableTest() throws DatabaseException {
        db.getUserDB().addUser(conn, addUserStatement);
        String personID = db.getUserDB().userInTable(conn, userInTable);
        assertTrue(personID != null);
    }

    @Test
    public void userNameFreeTest() throws DatabaseException {
        assertTrue(db.getUserDB().userNameFree(conn, userNameFreeStatement));
        db.getUserDB().addUser(conn, addUserStatement);
        assertFalse(db.getUserDB().userNameFree(conn, userNameFreeStatement));
    }

    @Test
    public void fillUserTest() throws DatabaseException {
        db.getUserDB().fillUser(conn, fillUserStatement);
        String personID = db.getUserDB().userInTable(conn, userInTable);
        String personID1 = db.getUserDB().userInTable(conn, userInTable1);
        assertTrue(personID != null && personID1 != null);

    }
}
