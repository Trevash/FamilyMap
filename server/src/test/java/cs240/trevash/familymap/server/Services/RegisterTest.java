package cs240.trevash.familymap.server.Services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.trevash.familymap.server.DBAccess.AuthTokenDBTest;
import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.server.DBAccess.PersonDBTest;
import cs240.trevash.familymap.server.DBAccess.UserDBTest;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/4/18.
 */

public class RegisterTest {
    private ConnectDB db = new ConnectDB();
    private Register register = new Register();
    private Clear clear = new Clear();
    private PersonDBTest personDBTest = new PersonDBTest();
    private UserDBTest userDBTest = new UserDBTest();
    private AuthTokenDBTest authTokenDBTest = new AuthTokenDBTest();

    @Before
    public void setUp() throws DatabaseException {
        ConnectDB db = new ConnectDB();
        db.openConnection();
        Connection conn = db.getConn();
        db.getClearDB().clearDB(conn);
        db.closeConnection(true);
    }

    @Test
    public void RegisterUserTest() {
        try {
            RegisterRequest registerRequest = new RegisterRequest("uName", "password", "email@mail.com", "fn", "ln", "M");
            String registeredUUID = register.RegisterUser(registerRequest).getCurrAuth().toString();
            db.openConnection();
            Connection conn = db.getConn();
            String personID = db.getUserDB().userInTable(conn, userDBTest.userInTable);
            Person person = db.getPersonDB().getPerson(conn, personDBTest.getPersonStatementRegisterTest);
            String checkAuthStatement = "SELECT userName FROM AuthTokenTable WHERE authToken = '" + registeredUUID + "';";
            String userName = db.getAuthTokenDB().isValidAuth(conn, checkAuthStatement);
            assertTrue(personID != null && person.getDescendantUsername().equals("uName") && userName != null);
            db.closeConnection(true);
            //clear.clear();
        }
        catch(DatabaseException e) {
            e.printStackTrace();
            Assert.fail("There was an exception in the database");
        }
    }

}
