package cs240.trevash.familymap.server.Services;

import org.junit.Test;

import cs240.trevash.familymap.shared.Objects.ClearFillResult;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/4/18.
 */

public class ClearTest {

    @Test
    public void ClearTest() {
        Clear clear = new Clear();
        ClearFillResult clearFillResult = clear.clear();
        assertTrue(clearFillResult.getMessage().equals("Successful removal!"));
    }
}
