package cs240.trevash.familymap.shared.Services;

/**
 * Created by trevash on 3/2/18.
 */

import org.junit.Test;

import cs240.trevash.familymap.shared.Objects.FNames;
import cs240.trevash.familymap.shared.Objects.Locations;
import cs240.trevash.familymap.shared.Objects.SNames;

import static org.junit.Assert.assertTrue;

public class JSONSampleReaderTest {
    private JSONSampleReader jsonSampleReader = new JSONSampleReader();

    @Test
    public void collectDataTest() {
        try {
            jsonSampleReader.collectData();
            FNames firstNames = jsonSampleReader.getFirstNames();
            SNames surnames = jsonSampleReader.getSurnames();
            Locations locations = jsonSampleReader.getLocations();
            assertTrue(firstNames.getNames().size() > 0);
            assertTrue(surnames.getNames().size() > 0);
            assertTrue(locations.getLocations().size() > 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
