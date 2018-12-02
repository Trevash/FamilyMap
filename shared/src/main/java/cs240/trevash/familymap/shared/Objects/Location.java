package cs240.trevash.familymap.shared.Objects;

/**
 * Created by trevash on 3/6/18.
 */

/**
 * Objects for use with the Locations object.
 * Store information about location for when events are generated.
 */
public class Location {
    private String country;
    private String city;
    private double latitude;
    private double longitude;

    public Location(String country, String city, double latitude, double longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
