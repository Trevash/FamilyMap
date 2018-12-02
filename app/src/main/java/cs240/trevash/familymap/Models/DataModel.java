package cs240.trevash.familymap.Models;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Models.User;

/**
 * Created by trevash on 3/22/18.
 */

public class DataModel {

    private Map<String, Person> ancestors;
    private Map<String, Event> events;
    private Map<String, Event> filteredEvents;
    private Map<String, ArrayList<Event>> personEvents;
    private Person user;
    private String userID;
    private UUID authToken;
    private String host;
    private String port;
    private List<String> fathersSide = new ArrayList<>();
    private List<String> mothersSide = new ArrayList<>();

    private boolean BirthEventsSelected = true;
    private boolean MarriageEventsSelected = true;
    private boolean DeathEventsSelected = true;
    private boolean fathersSideSelected = true;
    private boolean mothersSideSelected = true;
    private boolean maleEventsSelected = true;
    private boolean femaleEventsSelected = true;

    private LineColor lifeStoryLineColor = LineColor.Red;
    private boolean lifeStorySwitchChecked = true;
    private LineColor FamilyLineColor = LineColor.Green;
    private boolean familySwitchChecked = true;
    private LineColor SpouseLineColor = LineColor.Blue;
    private boolean spouseSwitchChecked = true;
    private MapType selectedMapType = MapType.Normal;

    private static DataModel sDataModel;

    private DataModel() {}

    public static DataModel getDataModel() {
        if (sDataModel == null) {
            sDataModel = new DataModel();
        }
        return sDataModel;
    }

    public void wipe() {
        sDataModel = null;
    }

    public void clean() {
        ancestors = null;
        events = null;
        filteredEvents = null;
        personEvents = null;
    }

    private void findFathersSide(Person currPerson) {
        if (currPerson.getFatherId() != null) {
            fathersSide.add(currPerson.getFatherId());
            findFathersSide(getAncestors().get(currPerson.getFatherId()));
        }
        if (currPerson.getMotherId() != null) {
            fathersSide.add(currPerson.getMotherId());
            findFathersSide(getAncestors().get(currPerson.getMotherId()));
        }
    }

    private void findMothersSide(Person currPerson) {
        if (currPerson.getFatherId() != null) {
            mothersSide.add(currPerson.getFatherId());
            findMothersSide(getAncestors().get(currPerson.getFatherId()));
        }
        if (currPerson.getMotherId() != null) {
            mothersSide.add(currPerson.getMotherId());
            findMothersSide(getAncestors().get(currPerson.getMotherId()));
        }
    }

    private boolean onFathersSide(String id) {
        for (int i = 0; i < fathersSide.size(); i++) {
            if (fathersSide.get(i).equals(id)) return true;
        }
        return false;
    }

    private boolean onMothersSide(String id) {
        for (int i = 0; i < mothersSide.size(); i++) {
            if (mothersSide.get(i).equals(id)) return true;
        }
        return false;
    }

    // ---- Filters events and determines the side of the family person is on ----

    public void filterEvents() {

        Map<String, Event> Events = getEvents();
        Map<String, Event> FilteredEvents = new HashMap<>();

        Person user = getUser();
        fathersSide.clear();
        mothersSide.clear();
        fathersSide.add(user.getFatherId());
        mothersSide.add(user.getMotherId());
        findFathersSide(getAncestors().get(user.getFatherId()));
        findMothersSide(getAncestors().get(user.getMotherId()));

        for (Map.Entry<String, Event> mapEntry : Events.entrySet()) {
            String id = mapEntry.getKey();
            Event event = mapEntry.getValue();
            Person person = getAncestors().get(event.getPersonId());

            if (isFathersSideSelected() && onFathersSide(person.getPersonId())) {
                if (person.getGender().equals("M") && isMaleEventsSelected()) {
                    if (event.getEventType().equals("Birth") && isBirthEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Marriage") && isMarriageEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Death") && isDeathEventsSelected())
                        FilteredEvents.put(id, event);
                }
                if (person.getGender().equals("F") && isFemaleEventsSelected()) {
                    if (event.getEventType().equals("Birth") && isBirthEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Marriage") && isMarriageEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Death") && isDeathEventsSelected())
                        FilteredEvents.put(id, event);
                }
            }
            if (isMothersSideSelected() && onMothersSide(person.getPersonId())) {
                if (person.getGender().equals("M") && isMaleEventsSelected()) {
                    if (event.getEventType().equals("Birth") && isBirthEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Marriage") && isMarriageEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Death") && isDeathEventsSelected())
                        FilteredEvents.put(id, event);
                }
                if (person.getGender().equals("F") && isFemaleEventsSelected()) {
                    if (event.getEventType().equals("Birth") && isBirthEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Marriage") && isMarriageEventsSelected())
                        FilteredEvents.put(id, event);
                    else if (event.getEventType().equals("Death") && isDeathEventsSelected())
                        FilteredEvents.put(id, event);
                }
            }

        }

        setFilteredEvents(FilteredEvents);
    }

    public enum LineColor {
        Red,
        Green,
        Blue,
        Yellow,
        Purple
    }

    public enum MapType {
        Normal,
        Hybrid,
        Satellite,
        Terrain
    }

    public Map<String, Person> getAncestors() {
        return ancestors;
    }

    public void setAncestors(Map<String, Person> ancestors) {
        this.ancestors = ancestors;
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Event> events) {
        this.events = events;
    }

    public Map<String, Event> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(Map<String, Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public Map<String, ArrayList<Event>> getPersonEvents() {
        return personEvents;
    }

    public void setPersonEvents(Map<String, ArrayList<Event>> personEvents) {
        this.personEvents = personEvents;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UUID getAuthToken() {
        return authToken;
    }

    public void setAuthToken(UUID authToken) {
        this.authToken = authToken;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<String> getFathersSide() {
        return fathersSide;
    }

    public void setFathersSide(List<String> fathersSide) {
        this.fathersSide = fathersSide;
    }

    public List<String> getMothersSide() {
        return mothersSide;
    }

    public void setMothersSide(List<String> mothersSide) {
        this.mothersSide = mothersSide;
    }

    public boolean isBirthEventsSelected() {
        return BirthEventsSelected;
    }

    public void setBirthEventsSelected(boolean BirthEventsSelected) {
        this.BirthEventsSelected = BirthEventsSelected;
    }

    public boolean isMarriageEventsSelected() {
        return MarriageEventsSelected;
    }

    public void setMarriageEventsSelected(boolean MarriageEventsSelected) {
        this.MarriageEventsSelected = MarriageEventsSelected;
    }

    public boolean isDeathEventsSelected() {
        return DeathEventsSelected;
    }

    public void setDeathEventsSelected(boolean DeathEventsSelected) {
        this.DeathEventsSelected = DeathEventsSelected;
    }

    public boolean isFathersSideSelected() {
        return fathersSideSelected;
    }

    public void setFathersSideSelected(boolean fathersSideSelected) {
        this.fathersSideSelected = fathersSideSelected;
    }

    public boolean isMothersSideSelected() {
        return mothersSideSelected;
    }

    public void setMothersSideSelected(boolean mothersSideSelected) {
        this.mothersSideSelected = mothersSideSelected;
    }

    public boolean isMaleEventsSelected() {
        return maleEventsSelected;
    }

    public void setMaleEventsSelected(boolean maleEventsSelected) {
        this.maleEventsSelected = maleEventsSelected;
    }

    public boolean isFemaleEventsSelected() {
        return femaleEventsSelected;
    }

    public void setFemaleEventsSelected(boolean femaleEventsSelected) {
        this.femaleEventsSelected = femaleEventsSelected;
    }

    public LineColor getLifeStoryLineColor() {
        return lifeStoryLineColor;
    }

    public void setLifeStoryLineColor(LineColor lifeStoryLineColor) {
        this.lifeStoryLineColor = lifeStoryLineColor;
    }

    public boolean isLifeStorySwitchChecked() {
        return lifeStorySwitchChecked;
    }

    public void setLifeStorySwitchChecked(boolean lifeStorySwitchChecked) {
        this.lifeStorySwitchChecked = lifeStorySwitchChecked;
    }

    public LineColor getFamilyLineColor() {
        return FamilyLineColor;
    }

    public void setFamilyLineColor(LineColor familyLineColor) {
        FamilyLineColor = familyLineColor;
    }

    public boolean isFamilySwitchChecked() {
        return familySwitchChecked;
    }

    public void setFamilySwitchChecked(boolean familySwitchChecked) {
        this.familySwitchChecked = familySwitchChecked;
    }

    public LineColor getSpouseLineColor() {
        return SpouseLineColor;
    }

    public void setSpouseLineColor(LineColor spouseLineColor) {
        SpouseLineColor = spouseLineColor;
    }

    public boolean isSpouseSwitchChecked() {
        return spouseSwitchChecked;
    }

    public void setSpouseSwitchChecked(boolean spouseSwitchChecked) {
        this.spouseSwitchChecked = spouseSwitchChecked;
    }

    public MapType getMapType() {
        return selectedMapType;
    }

    public void setSelectedMapType(MapType selectedMapType) {
        this.selectedMapType = selectedMapType;
    }

}
