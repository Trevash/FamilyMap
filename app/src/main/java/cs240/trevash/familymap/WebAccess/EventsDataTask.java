package cs240.trevash.familymap.WebAccess;

import android.os.AsyncTask;

import java.net.URL;

import cs240.trevash.familymap.shared.Objects.EventsResult;

/**
 * Created by trevash on 3/26/18.
 */

public class EventsDataTask extends AsyncTask<URL, Integer, EventsResult> {

    public interface Context {
        //void registerInfo(RegisterLoginResult RegisterResult);
        void eventInfo(EventsResult eventsResult);
        //void gatherAncestorInfo(PersonsResult ancestors, EventsResult events);
    }

    private Context mContextInfo;
    private String personID;
    private String authToken;

    public EventsDataTask(Context c, String authToken) {
        this.mContextInfo = c;
        this.authToken = authToken;
    }

    public EventsDataTask(Context c, String authToken, String personID) {
        this.mContextInfo = c;
        this.authToken = authToken;
        this.personID = personID;
    }


    protected EventsResult doInBackground(URL...urls) {
        HttpClient httpClient = new HttpClient();
        EventsResult urlContent;
        //if (personID != null) {
        //    urlContent = httpClient.getEvent(urls[0], mRequest);
        //}
        //else {
            urlContent = httpClient.getEvents(urls[0], authToken);
        //}
        return urlContent;
    }

    protected void onPostExecute(EventsResult result) {
        mContextInfo.eventInfo(result);
    }
}
