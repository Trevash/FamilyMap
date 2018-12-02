package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/27/18.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Event;

import static org.junit.Assert.assertTrue;

public class EventDBTest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;

    public String fillEventStatement = "INSERT INTO EventTable (eventID, descendant, personID, latitude, longitude, country, city, eventType, year)" +
            " VALUES ('uuid', 'dName', 'id', 'numbers', 'numbers', 'USA', 'Utah', 'Dance off', 1995), " +
            "('uuid1', 'dName', 'id', 'numbers', 'numbers', 'USA', 'Utah', 'Dance off', 1995);";
    public String getEventStatement = "SELECT eventID, descendant, personID, latitude, longitude, country, city, eventType, year FROM EventTable " +
            "WHERE eventID = 'uuid';";
    public String getEventsStatement = "SELECT eventID, descendant, personID, latitude, longitude, country, city, eventType, year FROM EventTable " +
            "WHERE personID = 'id';";

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
    public void fillEventTest() throws DatabaseException {
        db.getEventDB().fillEvent(conn, fillEventStatement);
        ArrayList<Event> myEvents = db.getEventDB().getEvents(conn, getEventsStatement);
        assertTrue(myEvents.size() == 2);
    }

    @Test
    public void getEventTest() throws DatabaseException {
        db.getEventDB().fillEvent(conn, fillEventStatement);
        Event myEvent = db.getEventDB().getEvent(conn, getEventStatement);
        assertTrue(myEvent != null);
    }

    @Test
    public void getEventsTest() throws DatabaseException {
        db.getEventDB().fillEvent(conn, fillEventStatement);
        ArrayList<Event> myEvents = db.getEventDB().getEvents(conn, getEventsStatement);
        assertTrue(myEvents.size() == 2);
    }
}
