package cs240.trevash.familymap.server.Services;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Objects.EventPersonList;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;
import cs240.trevash.familymap.shared.Services.generateEvent;
import cs240.trevash.familymap.shared.Services.generatePerson;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * Class used to register a new user
 */
public class Register {

    /**
     * Registers user and returns a new register result object. Will call User to check if user is present
     * Generate an authToken,
     * Insert AuthToken, Person, and User in respective databases
     * @param r register request object sent by user
     * @return Successfully registered register result object
     */

    public RegisterLoginResult RegisterUser(RegisterRequest r) {
        ConnectDB db = new ConnectDB();
        RegisterLoginResult regResult = null;
        generatePerson generatePerson = new generatePerson();
        generateEvent generateEvent = new generateEvent();

        try {
            db.openConnection();
            Connection conn = db.getConn();
            int generations = 4;
            UUID genAuthToken = UUID.randomUUID();

            //Verifies userName is free
            String checkUserStatement = "SELECT * FROM UserTable WHERE userName = '"+ r.getUserName() + "';";
            if (db.getUserDB().userNameFree(conn, checkUserStatement)) {

                EventPersonList eventPersonList = generatePerson.generatePersonsAndEventsForUser(generations, r);

                String usersPersonId = eventPersonList.getUserPersonId();
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

                String SQLFormattedEvents = generateEvent.generateForSQL(eventPersonList.getEvents());
                String insertEventsStatement = "INSERT INTO EventTable (eventID, descendant, personID, latitude, longitude, country, city, eventType, year) VALUES " + SQLFormattedEvents;
                db.getEventDB().fillEvent(conn, insertEventsStatement);

                //Adds user to user database
                String addUserStatement = "INSERT INTO UserTable (userName, password, email, firstName, lastName, gender, personID) VALUES ('" + r.getUserName() + "', '" + r.getPassword() + "', '" + r.getEmailAddress() + "', '" + r.getFirstName() + "', '" + r.getLastName() + "', '" + r.getGender() + "', '" + usersPersonId + "');";
                db.getUserDB().addUser(conn, addUserStatement);

                //generates an authToken for the user
                String addAuthStatement = "INSERT INTO AuthTokenTable (authToken, userName) VALUES ('" + genAuthToken + "', '" + r.getUserName() + "');";
                db.getAuthTokenDB().insertAuth(conn, addAuthStatement);

                db.closeConnection(true);
                regResult = new RegisterLoginResult(genAuthToken, r.getUserName(), usersPersonId);
            }
            else {
                System.out.println("User already in table");
                db.closeConnection(false);
                regResult = new RegisterLoginResult("userName is not free");
            }

            return regResult;

        }
        catch (Exception e) {
            try {
                db.closeConnection(false);
            } catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            return new RegisterLoginResult(e.getMessage());
        }
        finally {
            if (db != null) db = null;
            if (regResult != null) regResult = null;
        }
    }
}
