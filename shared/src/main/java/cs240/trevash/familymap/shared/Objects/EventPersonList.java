package cs240.trevash.familymap.shared.Objects;

import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;

/**
 * Created by trevash on 3/7/18.
 */

/**
 * Contains two arrayList: One for generated persons and one for generated events.
 * Also contains the personID generated for the user that is registering.
 */
public class EventPersonList {
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Person> persons = new ArrayList<>();
    private String userPersonId;

    public EventPersonList() {}

    public EventPersonList(ArrayList<Event> events, ArrayList<Person> persons) {
        this.events = events;
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public String getUserPersonId() {
        return userPersonId;
    }

    /**
     * Adds an event to the event arrayList
     * @param event - event to be added to arrayList
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Adds a person to the person arrayList
     * @param person - person object to be added to arrayList
     */
    public void addPerson(Person person) {
        persons.add(person);
    }

    public void setUserPersonId(String userPersonId) {
        this.userPersonId = userPersonId;
    }
}
