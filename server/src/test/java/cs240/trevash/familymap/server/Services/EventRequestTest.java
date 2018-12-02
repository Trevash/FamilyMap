package cs240.trevash.familymap.server.Services;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import cs240.trevash.familymap.server.DBAccess.AuthTokenDBTest;
import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.server.DBAccess.EventDBTest;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Objects.EventsResult;

import static org.junit.Assert.assertTrue;


public class EventRequestTest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;
    private UUID authToken;
    private EventDBTest eventDBTest = new EventDBTest();
    private EventRequest eventRequest = new EventRequest();
    private AuthTokenDBTest authTokenDBTest = new AuthTokenDBTest();

    @Test
    public void getAllByUserTest() throws SQLException, DatabaseException {
        db.openConnection();
        conn = db.getConn();
        db.getEventDB().fillEvent(conn, eventDBTest.fillEventStatement);
        db.getAuthTokenDB().insertAuth(conn, authTokenDBTest.insertStatement);
        db.closeConnection(true);
        EventsResult events =  eventRequest.getAllByUser("myAuthToken");
        db.openConnection();
        conn = db.getConn();
        db.getClearDB().clearDB(conn);
        db.closeConnection(true);
        assertTrue(events.getData().size() == 2);
    }

    @Test
    public void getEventTest() throws SQLException, DatabaseException {
        db.openConnection();
        conn = db.getConn();
        db.getEventDB().fillEvent(conn, eventDBTest.fillEventStatement);
        db.getAuthTokenDB().insertAuth(conn, authTokenDBTest.insertStatement);
        db.closeConnection(true);
        Event event = eventRequest.getEvent("uuid", "myAuthToken");
        db.openConnection();
        conn = db.getConn();
        db.getClearDB().clearDB(conn);
        db.closeConnection(true);
        assertTrue(event.getEventId().equals("uuid"));
    }
}