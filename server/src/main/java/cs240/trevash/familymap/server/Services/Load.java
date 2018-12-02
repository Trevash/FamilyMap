package cs240.trevash.familymap.server.Services;

import java.sql.Connection;
import java.util.ArrayList;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Objects.LoadRequest;
import cs240.trevash.familymap.shared.Objects.MessageObject;
import cs240.trevash.familymap.shared.Services.GenerateUser;
import cs240.trevash.familymap.shared.Services.generateEvent;
import cs240.trevash.familymap.shared.Services.generatePerson;

/**
 * Created by trevash on 2/15/18.
 */

/**
 * Service that will Remove everything from the database and fill it with new information
 */
public class Load {
    private ConnectDB db = new ConnectDB();
    private Connection conn;
    private cs240.trevash.familymap.shared.Services.generateEvent generateEvent = new generateEvent();
    private cs240.trevash.familymap.shared.Services.generatePerson generatePerson = new generatePerson();
    private GenerateUser generateUser = new GenerateUser();

    /**
     * Calls the ClearHandler service to remove everything from database
     * Receives a load object and forwards it to FillHandler service
     * @param lr - Object containing Event, Person, and User arrays
     */
    public MessageObject Load(LoadRequest lr) {
        try {
            db.openConnection();
            conn = db.getConn();
            db.getClearDB().clearDB(conn);

            ArrayList<String> stringsToInsert = generatePerson.generateForSQL(lr.getPersons());

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

            String fillEventStatement = "INSERT INTO EventTable (eventID, descendant, personID, latitude, longitude, country, city, eventType, year) VALUES " + generateEvent.generateForSQL(lr.getEvents()) + ";";
            db.getEventDB().fillEvent(conn, fillEventStatement);

            String fillUserStatement = "INSERT INTO UserTable (userName, password, email, firstName, lastName, gender, personID) VALUES " + generateUser.generateForSQL(lr.getUsers());
            db.getUserDB().fillUser(conn, fillUserStatement);

            db.closeConnection(true);

            int eventNum = lr.getEvents().size();
            int personNum = lr.getPersons().size();
            int userNum = lr.getUsers().size();
            return new MessageObject("Successfully added " + userNum + " users, " + personNum + " persons, and " + eventNum + " events to the database");
        }
        catch(DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return new MessageObject("Failed adding because of exception: " + e.getMessage());
        }

    }
}
