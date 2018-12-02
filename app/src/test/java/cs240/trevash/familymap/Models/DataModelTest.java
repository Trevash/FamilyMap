package cs240.trevash.familymap.Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.EventPersonList;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;
import cs240.trevash.familymap.shared.Services.generatePerson;

import static org.junit.Assert.assertTrue;

/**
 * Created by treva on 4/17/2018.
 */

public class DataModelTest {
    private DataModel dataModel = DataModel.getDataModel();

    @Before
    public void setup() throws Exception {
        generatePerson generatePerson = new generatePerson();
        EventPersonList eventPersonList = generatePerson.generatePersonsAndEventsForUser(3, new RegisterRequest("FN", "PW", "EA", "FN", "LN", "G"));
        List<Person> persons = eventPersonList.getPersons();
        List<Event> events = eventPersonList.getEvents();
        Map<String, Person> personMap = new HashMap<>();
        Map<String, Event> eventMap = new HashMap<>();

        for (int i = 0; i < persons.size(); i++) {
            personMap.put(persons.get(i).getPersonId(), persons.get(i));
        }

        for (int i = 0; i < events.size(); i++) {
            eventMap.put(events.get(i).getEventId(), events.get(i));
        }

        dataModel.setAncestors(personMap);
        dataModel.setEvents(eventMap);

        String personId = eventPersonList.getUserPersonId();

        Person user = personMap.get(personId);

        dataModel.setUser(user);
    }

    @After
    public void cleanUp() {
        dataModel.wipe();
    }

    @Test
    public void filterAllEventsTest() {
        dataModel.setBirthEventsSelected(false);
        dataModel.setMarriageEventsSelected(false);
        dataModel.setDeathEventsSelected(false);
        dataModel.filterEvents();

        Map<String, Event> filteredEvents = dataModel.getFilteredEvents();

        assertTrue(filteredEvents.size() == 0);
    }

    @Test
    public void filterBirthEvents() {
        dataModel.setBirthEventsSelected(false);
        dataModel.filterEvents();

        Map<String, Event> filteredEvents = dataModel.getFilteredEvents();

        assertTrue(filteredEvents.size() < dataModel.getEvents().size() && filteredEvents.size() > 0);
    }

    @Test
    public void filterAllFamily() {
        dataModel.setFathersSideSelected(false);
        dataModel.setMothersSideSelected(false);
        dataModel.filterEvents();

        Map<String, Event> filteredEvents = dataModel.getFilteredEvents();

        assertTrue(filteredEvents.size() == 0);
    }

    @Test
    public void filterFatherSideFamily() {
        dataModel.setFathersSideSelected(false);
        dataModel.filterEvents();

        Map<String, Event> filteredEvents = dataModel.getFilteredEvents();

        assertTrue(filteredEvents.size() < dataModel.getEvents().size() && filteredEvents.size() > 0);
    }

    @Test
    public void filterAllGender() {
        dataModel.setMaleEventsSelected(false);
        dataModel.setFemaleEventsSelected(false);
        dataModel.filterEvents();

        Map<String, Event> filteredEvents = dataModel.getFilteredEvents();

        assertTrue(filteredEvents.size() == 0);
    }

    @Test
    public void filterMaleGender() {
        dataModel.setMaleEventsSelected(false);
        dataModel.filterEvents();

        Map<String, Event> filteredEvents = dataModel.getFilteredEvents();

        assertTrue(filteredEvents.size() < dataModel.getEvents().size() && filteredEvents.size() > 0);
    }
}
