package cs240.trevash.familymap.shared.Services;

import com.google.gson.Gson;

/**
 * Created by trevash on 3/1/18.
 */


public class Encoder {

    public String createJSON(Object objToConvert) {
        Gson converter = new Gson();
        return converter.toJson(objToConvert);
    }
}
