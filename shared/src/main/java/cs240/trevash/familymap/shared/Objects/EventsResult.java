package cs240.trevash.familymap.shared.Objects;

import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Event;

/**
 * Created by trevash on 3/5/18.
 */

/**
 * Result object when a person requests for a series of events
 */
public class EventsResult {
    private ArrayList<Event> data;
    private String message;

    public EventsResult(ArrayList<Event> events) {
        this.data = events;
    }

    public EventsResult(String message) {
        this.message = message;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
