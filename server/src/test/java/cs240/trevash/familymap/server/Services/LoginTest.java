package cs240.trevash.familymap.server.Services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.server.DBAccess.UserDBTest;
import cs240.trevash.familymap.shared.Objects.LoginRequest;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/4/18.
 */

public class LoginTest {
    private ConnectDB db = new ConnectDB();
    private UserDBTest userDBTest = new UserDBTest();
    private Login login = new Login();
    private Clear clear = new Clear();

    @Before
    public void setUp() throws DatabaseException {
        ConnectDB db = new ConnectDB();
        db.openConnection();
        Connection conn = db.getConn();
        db.getClearDB().clearDB(conn);
        db.closeConnection(true);
    }

    @Test
    public void LoginRequestTest() {
        try {
            db.openConnection();
            Connection connection = db.getConn();
            db.getUserDB().addUser(connection, userDBTest.addUserStatement);
            db.closeConnection(true);
            LoginRequest loginRequest = new LoginRequest("uName", "password");
            login.LoginUser(loginRequest);
            db.openConnection();
            connection = db.getConn();
            String personID = db.getUserDB().userInTable(connection, userDBTest.userInTable);
            assertTrue(personID != null);
            db.closeConnection(true);
            clear.clear();
        }
        catch(DatabaseException e) {
            e.printStackTrace();
            Assert.fail("There was an exception in the database");
        }
    }
}
