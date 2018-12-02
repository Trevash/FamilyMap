package cs240.trevash.familymap.server.Services;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;

import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.server.DBAccess.EventDBTest;
import cs240.trevash.familymap.server.DBAccess.PersonDBTest;
import cs240.trevash.familymap.server.DBAccess.UserDBTest;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Models.User;
import cs240.trevash.familymap.shared.Objects.LoadRequest;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/4/18.
 */

public class LoadTest {

    @Before
    public void setUp() throws DatabaseException {
        ConnectDB db = new ConnectDB();
        db.openConnection();
        Connection conn = db.getConn();
        db.getClearDB().clearDB(conn);
        db.closeConnection(true);
    }

    @Test
    public void loadTest() throws DatabaseException {
        Load load = new Load();
        PersonDBTest personDBTest = new PersonDBTest();
        EventDBTest eventDBTest = new EventDBTest();
        UserDBTest userDBTest = new UserDBTest();

        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        events.add(new Event("uuid", "dName", "pid", "la","LO", "CO", "CI", "ET", 1994));
        persons.add(new Person("id", "DU", "FN", "LN", "M"));
        users.add(new User("id", "uName", "password", "ea", "FN", "LN", "M"));

        LoadRequest loadRequest = new LoadRequest(events, persons, users);
        load.Load(loadRequest);

        ConnectDB db = new ConnectDB();
        db.openConnection();
        Connection conn = db.getConn();
        assertTrue(db.getPersonDB().getPerson(conn, personDBTest.getPersonStatement).getPersonId().equals("id"));
        assertTrue(db.getEventDB().getEvent(conn, eventDBTest.getEventStatement).getEventId().equals("uuid"));
        db.closeConnection(true);
    }
}
