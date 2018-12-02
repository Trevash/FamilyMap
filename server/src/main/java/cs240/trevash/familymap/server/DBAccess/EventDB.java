package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/13/18.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Event;

/**
 * This class will insert rows into the Event table for users and for Persons associated with that user
 */
public class EventDB {

    /**
     * Fills the database with the Event objects provided by the FillDB class
     * @param conn - Connection object to interact with the database
     * @param es - Events to enter into the database
     */
    public void fillEvent(Connection conn, String es) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(es);
        }
        catch (SQLException e) {
            throw new DatabaseException("Fill event failed", e);
        }
    }

    /**
     * Queries database for specified event
     * @param conn - Connection object to interact with the database
     * @param event - EventId desired by request
     * @return - Returns desired event object or null
     */
    public Event getEvent(Connection conn, String event) throws DatabaseException {
        try {
            Event soughtEvent = null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(event);
            if (rs.next()) {
                String eventID = rs.getString(1);
                String descendant = rs.getString(2);
                String personID = rs.getString(3);
                String latitude = rs.getString(4);
                String longitude = rs.getString(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);

                soughtEvent = new Event(eventID, descendant, personID, latitude, longitude, country, city, eventType, year);
            }

            return soughtEvent;
        }
        catch (SQLException e) {
            throw new DatabaseException("Getting event failed", e);
        }
    }

    /**
     * Collects all events from the database that have userName as their descendantId
     * @param conn - Connection object to interact with the database
     * @param events - Events desired by request
     * @return - Returns arrayList of desired event objects
     * @throws SQLException - If the database has any connection issues
     */
    public ArrayList<Event> getEvents(Connection conn, String events) throws DatabaseException {
        try {
            ArrayList<Event> soughtEvents = new ArrayList<Event>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(events);
            while (rs.next()) {
                String eventID = rs.getString(1);
                String descendant = rs.getString(2);
                String personID = rs.getString(3);
                String latitude = rs.getString(4);
                String longitude = rs.getString(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);

                Event soughtEvent = new Event(eventID, descendant, personID, latitude, longitude, country, city, eventType, year);
                soughtEvents.add(soughtEvent);
            }

            return soughtEvents;
        }
        catch (SQLException e) {
            throw new DatabaseException("Getting events for user failed", e);
        }
    }
}
