package cs240.trevash.familymap.shared.Services;

/**
 * Created by trevash on 3/2/18.
 */

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;

import cs240.trevash.familymap.shared.Objects.FNames;
import cs240.trevash.familymap.shared.Objects.Locations;
import cs240.trevash.familymap.shared.Objects.SNames;

/**
 * Reads in and stores JSON samples in their different file structures
 */
public class JSONSampleReader {

    public JSONSampleReader() {
        try {
            collectData();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Locations locations;
    private FNames firstNames;
    private SNames surnames;

    private void collectFirstNames(File file) throws Exception {
        FileReader myReader = new FileReader(file);
        Gson gson = new Gson();
        firstNames = gson.fromJson(myReader, FNames.class);
    }

    private void collectLastNames(File file) throws Exception {
        FileReader myReader = new FileReader(file);
        Gson gson = new Gson();
        surnames = gson.fromJson(myReader, SNames.class);
    }

    private void collectLocations(File file) throws Exception {
        FileReader myReader = new FileReader(file);
        Gson gson = new Gson();
        locations = gson.fromJson(myReader, Locations.class);
    }

    public void collectData() throws Exception {
        collectFirstNames(new File("shared/jsonSamples/fnames.json"));
        collectLastNames(new File("shared/jsonSamples/snames.json"));
        collectLocations(new File("shared/jsonSamples/locations.json"));
    }

    public Locations getLocations() {
        return locations;
    }

    public FNames getFirstNames() {
        return firstNames;
    }

    public SNames getSurnames() {
        return surnames;
    }
}
