package cs240.trevash.familymap.WebAccess;

import android.os.AsyncTask;

import java.net.URL;

import cs240.trevash.familymap.shared.Objects.LoginRequest;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;

/**
 * Created by trevash on 3/24/18.
 */

//TODO: SEND URL and LoginObject together
public class LoginTask extends AsyncTask<URL, Integer, RegisterLoginResult> {

    public interface Context {
        //void registerInfo(RegisterLoginResult RegisterResult);
        void loginInfo(RegisterLoginResult LoginResult);
        //void gatherAncestorInfo(PersonsResult ancestors, EventsResult events);
    }

    private Context mContextInfo;
    private LoginRequest mRequest;

    public LoginTask(Context c, LoginRequest loginRequest) {
        mContextInfo = c;
        mRequest = loginRequest;
    }


    protected RegisterLoginResult doInBackground(URL...urls) {
        HttpClient httpClient = new HttpClient();
        RegisterLoginResult urlContent = httpClient.login(urls[0], mRequest);
        return urlContent;
    }

    protected void onPostExecute(RegisterLoginResult result) {
        mContextInfo.loginInfo(result);
    }
}
