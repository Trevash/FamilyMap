package cs240.trevash.familymap.server.Services;

import java.sql.Connection;
import java.util.ArrayList;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.PersonsResult;

/**
 * Created by trevash on 2/15/18.
 */

/**
 * Forwards requests to the database that request for Persons and returns those Person objects
 */
public class PersonRequest {
    private ConnectDB db = new ConnectDB();
    private Connection conn;

    /**
     * Queries PersonDB to see if Person exists in database and returns the person if they do
     * @param personID - The ID of the person the user wants to collect
     * @return - returns the Person object associated with the person
     */
    public Person getPerson(String personID, String authToken) {
        try {
            Person person;
            db.openConnection();
            conn = db.getConn();
            String authTokenCheck = "SELECT userName FROM AuthTokenTable WHERE authToken = '" + authToken + "';";
            String userName = db.getAuthTokenDB().isValidAuth(conn, authTokenCheck);

            if (userName != null) {
                String getByIdStatement = "SELECT personID, descendant, firstName, lastName, gender, father, mother, spouse FROM PersonTable WHERE personID = '" + personID + "';";
                person = db.getPersonDB().getPerson(conn, getByIdStatement);

                if (person != null && !person.getDescendantUsername().equals(userName)) {
                    db.closeConnection(false);
                    return new Person("User is not authorized to request for this person");
                }
            }
            else person = new Person("User is not authorized to make request");

            db.closeConnection(true);
            return person;
        }
        catch(DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (DatabaseException e1) {
                e1.printStackTrace();
            }
            return new Person("Internal Error: " + e.getMessage());
        }
    }

    /**
     * Queries PersonDB for all the Persons that have the User as their descendant
     * @param authToken - authToken of the User
     * @return - Returns an array of all the Person objects that have User's userName as their descendant - or error message if it fails
     */
    public PersonsResult getAllByUser(String authToken) {
        try {
            PersonsResult result;
            db.openConnection();
            conn = db.getConn();
            String authTokenCheck = "SELECT userName FROM AuthTokenTable WHERE authToken = '" + authToken + "';";
            String userName = db.getAuthTokenDB().isValidAuth(conn, authTokenCheck);

            if (userName != null) {
                String getByUserStatement = "SELECT personID, descendant, firstName, lastName, gender, father, mother, spouse FROM PersonTable WHERE descendant = '" + userName + "';";
                ArrayList<Person> persons = db.getPersonDB().getPersons(conn, getByUserStatement);
                result = new PersonsResult(persons);
            }
            else result = new PersonsResult("No userName associated with authToken");
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
            return new PersonsResult("Failed querying database because of: " + e.getMessage());
        }
    }
}
