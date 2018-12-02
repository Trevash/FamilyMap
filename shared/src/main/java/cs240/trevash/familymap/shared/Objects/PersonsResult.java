package cs240.trevash.familymap.shared.Objects;

import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Person;

/**
 * Created by trevash on 3/5/18.
 */

/**
 * Result object for when user makes a Person request
 * Stores arrayList of person objects or error message
 */
public class PersonsResult {
    private ArrayList<Person> data;
    private String message;

    public PersonsResult(ArrayList<Person> persons) {
        this.data = persons;
    }

    public PersonsResult(String message) {
        this.message = message;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
