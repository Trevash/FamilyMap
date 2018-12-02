package cs240.trevash.familymap.server.Services;

import java.sql.Connection;
import java.util.ArrayList;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Objects.EventsResult;

/**
 * Created by trevash on 2/15/18.
 */

/**
 * Forwards requests to the database that deal with collecting events and returns those events
 */
public class EventRequest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;
    /**
     * Queries EventDB to see if Event exists in database and returns the Event if they do
     * @param EventId - The ID of the Event the user wants to collect
     * @return - returns the Event object associated with the Event
     */
    public Event getEvent(String EventId, String authToken) {
        try {
            Event event;
            db.openConnection();
            conn = db.getConn();

            String authTokenCheck = "SELECT userName FROM AuthTokenTable WHERE authToken = '" + authToken + "';";
            String userName = db.getAuthTokenDB().isValidAuth(conn, authTokenCheck);

            if (userName != null) {
                String eventSelectStatement = "SELECT eventID, descendant, personID, latitude, longitude, country, city, eventType, year FROM EventTable WHERE eventID = '" + EventId + "';";
                event = db.getEventDB().getEvent(conn, eventSelectStatement);

                if (event != null && !event.getDescendantUsername().equals(userName)) {
                    db.closeConnection(false);
                    return new Event("userName not authorized to request for this event data");
                }
            }
            else event = new Event("authToken not associated with any userName");

            db.closeConnection(true);
            return event;
        }
        catch(DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return new Event("Failed connecting to database: " + e.getMessage());
        }

    }

    /**
     * Queries EventDB for all the Events that have the User as their descendant
     * @param authToken - authToken of the User
     * @return - Returns an array of all the Event objects that have User's userName as their descendant
     */
    public EventsResult getAllByUser(String authToken) {
        try {
            EventsResult result;
            db.openConnection();
            conn = db.getConn();

            String authTokenCheck = "SELECT userName FROM AuthTokenTable WHERE authToken = '" + authToken + "';";
            String userName = db.getAuthTokenDB().isValidAuth(conn, authTokenCheck);

            if (userName != null) {
                String eventCollectionStatement = "SELECT eventID, descendant, personID, latitude, longitude, country, city, eventType, year FROM EventTable WHERE descendant = '" + userName + "';";
                ArrayList<Event> events = db.getEventDB().getEvents(conn, eventCollectionStatement);
                result = new EventsResult(events);
            }
            else result = new EventsResult("No userName associated with given authToken");

            db.closeConnection(true);
            return result;
        }
        catch(DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return new EventsResult("Failed connecting to database: " + e.getMessage());
        }
    }
}
