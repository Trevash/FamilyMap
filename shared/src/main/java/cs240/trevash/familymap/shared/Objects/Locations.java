package cs240.trevash.familymap.shared.Objects;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by trevash on 3/6/18.
 */

/**
 * Stores Location objects for use when Events are generated.
 */
public class Locations {

    private ArrayList<Location> Locations;

    public Locations(ArrayList<Location> Locations) {
        this.Locations = Locations;
    }

    public ArrayList<Location> getLocations() {
        return Locations;
    }

    /**
     * Generates and returns a random location for a created Event Object
     * @return - returns a random Location object
     */
    public Location chooseRandomLocation() {
        Random myRandomGenerator = new Random();

        int index = myRandomGenerator.nextInt(Locations.size());
        return Locations.get(index);
    }

    /**
     * Adds a location object to Locations arrayList
     * @param location - location object to be added
     */
    public void add(Location location) {
        Locations.add(location);
    }
}
