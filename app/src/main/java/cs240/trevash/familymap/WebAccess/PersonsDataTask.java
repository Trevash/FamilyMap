package cs240.trevash.familymap.WebAccess;

import android.os.AsyncTask;

import java.net.URL;

import cs240.trevash.familymap.shared.Objects.PersonsResult;

/**
 * Created by trevash on 3/26/18.
 */

public class PersonsDataTask extends AsyncTask<URL, Integer, PersonsResult> {

    public interface Context {
        //void registerInfo(RegisterLoginResult RegisterResult);
        void personInfo(PersonsResult personsResult);
        //void gatherAncestorInfo(PersonsResult ancestors, EventsResult events);
    }

    private Context mContextInfo;
    private String personID;
    private String authToken;

    public PersonsDataTask(Context c, String authToken) {
        this.mContextInfo =  c;
        this.authToken = authToken;
    }

    public PersonsDataTask(Context c, String authToken, String personID) {
        this.mContextInfo = c;
        this.authToken = authToken;
        this.personID = personID;
    }


    protected PersonsResult doInBackground(URL...urls) {
        HttpClient httpClient = new HttpClient();
        PersonsResult urlContent;
        //if (personID != null) {
        //    urlContent = httpClient.getPerson(urls[0], personID);
        //}
        //else {
            urlContent = httpClient.getPersons(urls[0], authToken);
        //}
        return urlContent;
    }

    protected void onPostExecute(PersonsResult result) {
        mContextInfo.personInfo(result);
    }

}
