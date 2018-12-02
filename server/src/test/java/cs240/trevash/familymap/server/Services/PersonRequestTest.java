package cs240.trevash.familymap.server.Services;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import cs240.trevash.familymap.server.DBAccess.AuthTokenDBTest;
import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.server.DBAccess.PersonDBTest;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.PersonsResult;

import static org.junit.Assert.assertTrue;


public class PersonRequestTest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;
    private PersonDBTest personDBTest = new PersonDBTest();
    private PersonRequest personRequest = new PersonRequest();
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
    public void getAllByUserTest() throws SQLException, DatabaseException {
        db.openConnection();
        conn = db.getConn();
        db.getPersonDB().fillPerson(conn, personDBTest.addPersonsStatement);
        db.getAuthTokenDB().insertAuth(conn, authTokenDBTest.insertStatement);
        db.closeConnection(true);
        PersonsResult persons =  personRequest.getAllByUser("myAuthToken");
        assertTrue(persons.getData().size() == 2);
    }

    @Test
    public void getPersonTest() throws SQLException, DatabaseException {
        db.openConnection();
        conn = db.getConn();
        db.getPersonDB().addPerson(conn, personDBTest.addPersonStatement);
        db.getAuthTokenDB().insertAuth(conn, authTokenDBTest.insertStatement);
        db.closeConnection(true);
        Person person = personRequest.getPerson("id", "myAuthToken");
        assertTrue(person.getPersonId().equals("id"));
    }
}
