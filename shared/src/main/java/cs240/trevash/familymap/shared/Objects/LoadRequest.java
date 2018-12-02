package cs240.trevash.familymap.shared.Objects;

import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Models.User;

/**
 * Created by trevash on 2/15/18.
 */

/**
 * This Class is what will be used to create objects to hold data to load the database
 */
public class LoadRequest {
    private ArrayList<Event> events;
    private ArrayList<Person> persons;
    private ArrayList<User> users;

    public LoadRequest(ArrayList<Event> events, ArrayList<Person> persons, ArrayList<User> users) {
        this.events = events;
        this.persons = persons;
        this.users = users;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
