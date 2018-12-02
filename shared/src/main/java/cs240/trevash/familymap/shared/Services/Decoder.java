package cs240.trevash.familymap.shared.Services;

/**
 * Created by trevash on 3/1/18.
 */

import com.google.gson.Gson;

public class Decoder {

    public Object decodeJSON(String body, Class convertToClass) {
        Gson converter = new Gson();
        return converter.fromJson(body, convertToClass);
    }
}
