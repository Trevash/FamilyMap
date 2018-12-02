package cs240.trevash.familymap.shared.Services;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Objects.Location;
import cs240.trevash.familymap.shared.Objects.Locations;

/**
 * Created by trevash on 3/5/18.
 */

public class generateEvent {

    private Random randomGenerator = new Random();
    private JSONSampleReader jsonSampleReader = new JSONSampleReader();
    private Locations locations = jsonSampleReader.getLocations();

    public Event generateMarriage(String userName, String father, String mother) throws Exception {
        int year = randomGenerator.nextInt(180) + 1800;
        Location location = locations.chooseRandomLocation();
        return new Event(UUID.randomUUID().toString(), userName, father, Double.toString(location.getLatitude()), Double.toString(location.getLongitude()), location.getCountry(), location.getCity(), "Marriage", year);
    }

    public Event generateBirth(String userName, Event parentsMarriage, String personID) {
        int year = randomGenerator.nextInt(180) + 1800;
        if (parentsMarriage != null) {
            if (year < parentsMarriage.getYear()) year = parentsMarriage.getYear();
        }
        Location location = locations.chooseRandomLocation();
        return new Event(UUID.randomUUID().toString(), userName, personID, Double.toString(location.getLatitude()), Double.toString(location.getLongitude()), location.getCountry(), location.getCity(), "Birth", year);
    }

    public Event generateBaptism(String userName, Event Marriage, String personID) {
        int year = randomGenerator.nextInt(180) + 1800;
        if (Marriage != null) {
            if (year < Marriage.getYear()) year = Marriage.getYear();
        }
        Location location = locations.chooseRandomLocation();
        return new Event(UUID.randomUUID().toString(), userName, personID, Double.toString(location.getLatitude()), Double.toString(location.getLongitude()), location.getCountry(), location.getCity(), "Death", year);
    }

    public Event generateDeath(String userName, Event Marriage, String personID) {
        int year = randomGenerator.nextInt(180) + 1800;
        if (Marriage != null) {
            if (year < Marriage.getYear()) year = Marriage.getYear();
        }
        Location location = locations.chooseRandomLocation();
        return new Event(UUID.randomUUID().toString(), userName, personID, Double.toString(location.getLatitude()), Double.toString(location.getLongitude()), location.getCountry(), location.getCity(), "Death", year);
    }

    public String generateForSQL(ArrayList<Event> events) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < events.size(); i++) {
            stringBuilder.append("('");
            stringBuilder.append(events.get(i).getEventId());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(events.get(i).getDescendantUsername());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(events.get(i).getPersonId());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(events.get(i).getLatitude());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(events.get(i).getLongitude());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(events.get(i).getCountry());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(events.get(i).getCity());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(events.get(i).getEventType());
            stringBuilder.append("',");
            stringBuilder.append(events.get(i).getYear());
            stringBuilder.append("),");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
