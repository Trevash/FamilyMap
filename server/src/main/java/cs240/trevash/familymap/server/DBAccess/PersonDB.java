package cs240.trevash.familymap.server.DBAccess;

/**
 * Created by trevash on 2/13/18.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Person;

/**
 * Makes requests to the database for Person information and gathers person information
 */
public class PersonDB {

    /**
     * When a user registers, this will generate a person table row and return the value of the personID
     * @param conn - connection object for the database
     * @param u - Stringified sql statement to send to database
     */
    public void addPerson(Connection conn, String u) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(u);
        }
        catch (SQLException e) {
            throw new DatabaseException("Adding person failed", e);
        }
    }

    /**
     * Fills the database with the Person objects provided by the FillDB class
     * @param ps - Persons to enter into the database
     */
    public void fillPerson(Connection conn, String ps) throws DatabaseException {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(ps);
        }
        catch (SQLException e) {
            throw new DatabaseException("Filling person failed", e);
        }
    }

    /**
     * Queries database for person with given personID
     * @param p Stringified sql statement requesting given personID
     * @return Returns String Person Object or null
     */
    public Person getPerson(Connection conn, String p) throws DatabaseException {
        try {
            Person soughtPerson = null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(p);

            if (rs.next()) {
                String personID = rs.getString(1);
                String descendant = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String father = rs.getString(6);
                String mother = rs.getString(7);
                String spouse = rs.getString(8);

                soughtPerson = new Person(personID, descendant, firstName, lastName, gender, father, mother, spouse);
            }

            return soughtPerson;
        }
        catch (SQLException e) {
            throw new DatabaseException("Is valid auth check failed", e);
        }
    }

    /**
     * Queries database for all Persons that have user's PersonId as their DescendantId
     * @param ps - Stringified sql statement requesting for Persons that have user's PersonId as their DescendantId
     * @return Returns String object of all Persons that fit request
     */
    public ArrayList<Person> getPersons(Connection conn, String ps) throws DatabaseException {
        try {
            ArrayList<Person> persons = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ps);

            while (rs.next()) {
                String personID = rs.getString(1);
                String descendant = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String father = rs.getString(6);//How to check for null values
                String mother = rs.getString(7);
                String spouse = rs.getString(8);

                Person person = new Person(personID, descendant, firstName, lastName, gender);

                if (father != null) person.setFatherId(father);
                if (mother != null) person.setMotherId(mother);
                if (spouse != null) person.setSpouseId(spouse);

                persons.add(person);
            }

            return persons;
        }
        catch (SQLException e) {
            throw new DatabaseException("Is valid auth check failed", e);
        }
    }
}
