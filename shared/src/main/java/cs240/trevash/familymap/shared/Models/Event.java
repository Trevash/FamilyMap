package cs240.trevash.familymap.shared.Models;

/**
 * Created by trevash on 2/13/18.
 */

import java.util.List;

/**
 * Class Event - generates Events for Person objects within the Family Map
 */
public class Event {

    private List<Object> mEvents;

    private String message;
    private String eventID;//Can't be empty
    private String descendant;//Can't be empty
    private String personID;//Can't be empty
    private String latitude;//Can't be empty
    private String longitude;//Can't be empty
    private String country;//Can't be empty
    private String city;//Can't be empty
    private String eventType;//Can't be empty
    private Integer year;//Can't be empty

    public Event(String EID, String D, String PID, String LA, String LO, String CO, String CI, String ET, int Y) {
        this.eventID = EID;
        this.descendant = D;
        this.latitude = LA;
        this.longitude = LO;
        this.country = CO;
        this.city = CI;
        this.eventType = ET;
        this.year = Y;
        this.personID = PID;
    }

    public Event(String EID, String D, String PID, double LA, double LO, String CO, String CI, String ET, int Y) {
        this.eventID = EID;
        this.descendant = D;
        this.latitude = Double.toString(LA);
        this.longitude = Double.toString(LO);
        this.country = CO;
        this.city = CI;
        this.eventType = ET;
        this.year = Y;
        this.personID = PID;
    }
    public Event(String message) {
        this.message = message;
    }

    public String getEventId() {
        return eventID;
    }

    public String getDescendantUsername() {
        return descendant;
    }

    public String getPersonId() {
        return personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public Integer getYear() {
        return year;
    }

    public String getMessage() {
        return message;
    }
}
