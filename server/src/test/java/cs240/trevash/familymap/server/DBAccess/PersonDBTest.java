package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/27/18.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Person;

import static org.junit.Assert.assertTrue;

public class PersonDBTest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;

    public String addPersonStatement = "INSERT INTO PersonTable (personID, descendant, firstName, lastName, gender, father, mother, spouse) VALUES ('id', 'dName', 'FN', 'LN', 'M', 'fId', 'mId', 'sId');";
    public String addPersonsStatement = "INSERT INTO PersonTable (personID, descendant, firstName, lastName, gender, father, mother, spouse) VALUES ('id', 'dName', 'FN', 'LN', 'M', 'fId', 'mId', 'sId'), ('id1', 'dName', 'FN', 'LN', 'M', 'fId', 'mId', 'sId');";
    public String getPersonStatement = "SELECT personID, descendant, firstName, lastName, gender, father, mother, spouse FROM PersonTable WHERE personID = 'id';";
    public String getPersonStatementRegisterTest = "SELECT personID, descendant, firstName, lastName, gender, father, mother, spouse FROM PersonTable WHERE descendant = 'uName';";
    public String getPersonsStatement = "SELECT personID, descendant, firstName, lastName, gender, father, mother, spouse FROM PersonTable WHERE descendant = 'dName';";

    @Before
    public void setUp() throws DatabaseException, SQLException {
        db.openConnection();
        conn = db.getConn();
        db.getClearDB().clearDB(conn);
    }

    @After
    public void tearDown() throws DatabaseException {
        db.closeConnection(true);
    }

    @Test
    public void addPersonGetPersonTest() throws DatabaseException {
        db.getPersonDB().addPerson(conn, addPersonStatement);
        Person person = db.getPersonDB().getPerson(conn, getPersonStatement);
        assertTrue(person != null);
    }

    @Test
    public void fillPersonGetPersonsTest() throws DatabaseException {
        db.getPersonDB().fillPerson(conn, addPersonsStatement);
        ArrayList<Person> persons = db.getPersonDB().getPersons(conn, getPersonsStatement);
        assertTrue(persons.size() == 2);
    }
}
