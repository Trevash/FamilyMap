package cs240.trevash.familymap.WebAccess;

import android.os.AsyncTask;

import java.net.URL;

import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;

/**
 * Created by trevash on 3/26/18.
 */

public class RegisterTask extends AsyncTask<URL, Integer, RegisterLoginResult> {

    public interface Context {
        //void registerInfo(RegisterLoginResult RegisterResult);
        void registerInfo(RegisterLoginResult LoginResult);
        //void gatherAncestorInfo(PersonsResult ancestors, EventsResult events);
    }

    private Context mContextInfo;
    private RegisterRequest mRequest;

    public RegisterTask(Context c, RegisterRequest registerRequest) {
        mContextInfo = c;
        mRequest = registerRequest;
    }

    protected RegisterLoginResult doInBackground(URL...urls) {
        HttpClient httpClient = new HttpClient();
        RegisterLoginResult urlContent = httpClient.register(urls[0], mRequest);
        return urlContent;
    }

    protected void onPostExecute(RegisterLoginResult result) {
        mContextInfo.registerInfo(result);
    }
}
