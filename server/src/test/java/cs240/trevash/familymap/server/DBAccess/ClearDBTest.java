package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/27/18.
 */


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertTrue;

public class ClearDBTest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;
    private EventDBTest eventDBTest = new EventDBTest();
    private PersonDBTest personDBTest = new PersonDBTest();

    @Before
    public void setUp() throws DatabaseException {
        db.openConnection();
        conn = db.getConn();
        db.getClearDB().clearDB(conn);
    }

    @After
    public void tearDown() throws DatabaseException {
        db.closeConnection(true);
    }

    @Test
    public void clearDB() throws DatabaseException {
        db.getClearDB().clearDB(conn);
        assertTrue(db.getClearDB().isEmpty(conn));
    }

    @Test
    public void clearForUser() throws DatabaseException {
        db.getEventDB().fillEvent(conn, eventDBTest.fillEventStatement);
        db.getPersonDB().fillPerson(conn, personDBTest.addPersonsStatement);
        db.getClearDB().clearDB(conn);
        assertTrue(db.getClearDB().isEmpty(conn));
    }
}
