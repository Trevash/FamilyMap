package cs240.trevash.familymap.shared.Objects;

import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Models.User;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * Class to create a fill request object for a user attempting to fill the database with information
 * Holds three arrays: One for Events, Persons, and Users
 */
public class FillRequest {
    private Event[] Events;
    private Person[] Persons;
    private User[] Users;

    /**
     * Constructs a fill request object
     * @param U - users to fill db with
     * @param P - persons to fill db with
     * @param E - events to fill db with
     */
    public FillRequest(User[] U, Person[] P, Event[] E) {
        this.Users = U;
        this.Persons = P;
        this.Events = E;
    }

    public Event[] getEvents() {
        return Events;
    }

    public Person[] getPersons() {
        return Persons;
    }

    public User[] getUsers() {
        return Users;
    }
}
