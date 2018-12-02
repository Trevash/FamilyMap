package cs240.trevash.familymap.shared.Services;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import cs240.trevash.familymap.shared.Models.Event;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/5/18.
 */

public class generateEventTest {

    private generateEvent genEvent = new generateEvent();

    @Test
    public void generateMarriageTest() throws Exception {
        Event event = genEvent.generateMarriage("Fred", "father", "mother");
        assertTrue(event.getDescendantUsername().equals("Fred") && event.getPersonId().equals("father"));
    }

    @Test
    public void generateBirthTest() throws Exception {
        Event event = genEvent.generateMarriage("Fred", "father", "mother");
        Event Birth = genEvent.generateBirth("Fred", event, UUID.randomUUID().toString());
        assertTrue(Birth.getDescendantUsername().equals("Fred") && Birth.getYear() >= event.getYear());
    }

    @Test
    public void generateBaptismTest() throws Exception {
        Event Marriage = genEvent.generateMarriage("Fred", "father", "mother");
        Event baptism = genEvent.generateBaptism("Fred", Marriage, UUID.randomUUID().toString());
        assertTrue(baptism.getDescendantUsername().equals("Fred") && baptism.getYear() >= Marriage.getYear());
    }

    @Test
    public void generateDeathTest() throws Exception {
        Event Marriage = genEvent.generateMarriage("Fred", "father", "mother");
        Event Death = genEvent.generateDeath("Fred", Marriage, UUID.randomUUID().toString());
        assertTrue(Marriage.getDescendantUsername().equals("Fred") && Death.getYear() >= Marriage.getYear());
    }

    @Test
    public void generateForSQLTest() {
        ArrayList<Event> events = new ArrayList<>();
        Event event = new Event("id","dUName", "pId", "LA", "LO", "CO", "CI", "ET", 1995);
        events.add(event);
        String generatedEvents = genEvent.generateForSQL(events);
        assertTrue(generatedEvents.equals("('id','dUName','pId','LA','LO','CO','CI','ET',1995)"));
    }
}
