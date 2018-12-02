package cs240.trevash.familymap.server.Services;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.server.DBAccess.PersonDBTest;
import cs240.trevash.familymap.shared.Models.Person;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/4/18.
 */

public class FillTest {

    @Before
    public void setUp() throws DatabaseException {
        ConnectDB db = new ConnectDB();
        db.openConnection();
        Connection conn = db.getConn();
        db.getClearDB().clearDB(conn);
        db.closeConnection(true);
    }

    @Test
    public void fillTest() throws DatabaseException {
        PersonDBTest personDBTest = new PersonDBTest();
        Fill fill = new Fill();
        fill.fill("dName", 3);
        ConnectDB db = new ConnectDB();
        db.openConnection();
        Connection conn = db.getConn();
        Person person = db.getPersonDB().getPerson(conn, personDBTest.getPersonsStatement);
        assertTrue(person != null);
        db.closeConnection(false);
    }
}
