package cs240.trevash.familymap.shared.Services;

import org.junit.Test;

import cs240.trevash.familymap.shared.Objects.ClearFillResult;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/4/18.
 */

public class EncoderTest {

    @Test
    public void EncodeTest() {
        Encoder encoder = new Encoder();
        ClearFillResult clearFillResult = new ClearFillResult("This is my message");
        String message = encoder.createJSON(clearFillResult);
        assertTrue(message.equals("{\"message\":\"This is my message\"}"));
    }
}
