package cs240.trevash.familymap.server.Services;

import java.sql.Connection;
import java.util.ArrayList;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Objects.ClearFillResult;
import cs240.trevash.familymap.shared.Objects.EventPersonList;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;
import cs240.trevash.familymap.shared.Services.generateEvent;
import cs240.trevash.familymap.shared.Services.generatePerson;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * Class to fill the database with delivered fill request data
 */
public class Fill {
    private ConnectDB db = new ConnectDB();
    private cs240.trevash.familymap.shared.Services.generateEvent generateEvent = new generateEvent();
    private cs240.trevash.familymap.shared.Services.generatePerson generatePerson = new generatePerson();

    /**
     * Fills database and returns a message to user of a successful fill
     * @param userName - the userName to create the generations for
     * @param generations - the amount of generations for the user
     * @return - returns a clear fill request with a message
     */
    public ClearFillResult fill(String userName, int generations) {
        try {
            db.openConnection();
            Connection conn = db.getConn();

            db.getClearDB().clearForUser(conn, userName);

            //Generates arrayLists storing event and person objects to be inserted into database
            EventPersonList eventPersonList = generatePerson.generatePersonsAndEventsForUser(generations, new RegisterRequest(userName));

            //converts Person objects to SQL format
            ArrayList<String> stringsToInsert = generatePerson.generateForSQL(eventPersonList.getPersons());

            if (stringsToInsert.get(0).length() != 0) {
                String insertFullPersonStatement = "INSERT INTO PersonTable (personID, descendant, firstName, lastName, gender, father, mother, spouse) VALUES " + stringsToInsert.get(0);
                db.getPersonDB().fillPerson(conn, insertFullPersonStatement);
            }
            if (stringsToInsert.get(1).length() != 0) {
                String insertNoRelationStatement = "INSERT INTO PersonTable (personID, descendant, firstName, lastName, gender) Values " + stringsToInsert.get(1);
                db.getPersonDB().fillPerson(conn, insertNoRelationStatement);
            }
            if (stringsToInsert.get(2).length() != 0) {
                String insertParentRelationStatement = "INSERT INTO PersonTable (personID, descendant, firstName, lastName, gender, father, mother) VALUES " + stringsToInsert.get(2);
                db.getPersonDB().fillPerson(conn, insertParentRelationStatement);
            }
            if (stringsToInsert.get(3).length() != 0) {
                String insertSpouseRelationStatement = "INSERT INTO PersonTable (personID, descendant, firstName, lastName, gender, spouse) VALUES " + stringsToInsert.get(3);
                db.getPersonDB().fillPerson(conn, insertSpouseRelationStatement);
            }

            //Converts Event objects to SQL format
            String SQLFormattedEvents = generateEvent.generateForSQL(eventPersonList.getEvents());
            String insertEventsStatement = "INSERT INTO EventTable (eventID, descendant, personID, latitude, longitude, country, city, eventType, year) VALUES " + SQLFormattedEvents;
            db.getEventDB().fillEvent(conn, insertEventsStatement);

            db.closeConnection(true);

            int eventNum = eventPersonList.getEvents().size();
            int personNum = eventPersonList.getPersons().size();
            return new ClearFillResult("Successfully added " + eventNum + " events, and " + personNum + " persons.");
        }
        catch (Exception e) {

            try {
                db.closeConnection(false);
            } catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return new ClearFillResult("Failed adding, error: " + e.getMessage());
        }
    }
}
