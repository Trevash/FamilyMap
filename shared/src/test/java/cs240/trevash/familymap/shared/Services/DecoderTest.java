package cs240.trevash.familymap.shared.Services;

import org.junit.Test;

import cs240.trevash.familymap.shared.Objects.ClearFillResult;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/4/18.
 */

public class DecoderTest {
    private Decoder decoder = new Decoder();

    @Test
    public void decoderTest() {
        String jsonobj = "{\"message\": \"This is the greatest message\"}";
        Object result = decoder.decodeJSON(jsonobj, ClearFillResult.class);
        ClearFillResult clearFillResult = (ClearFillResult) result;
        assertTrue(clearFillResult.getMessage().equals("This is the greatest message"));
    }
}
