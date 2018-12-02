package cs240.trevash.familymap.WebAccess; /**
 * Created by trevash on 3/21/18.
 */

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;


import cs240.trevash.familymap.server.DBAccess.ConnectDB;
import cs240.trevash.familymap.server.DBAccess.DatabaseException;
import cs240.trevash.familymap.shared.Objects.EventsResult;
import cs240.trevash.familymap.shared.Objects.LoginRequest;
import cs240.trevash.familymap.shared.Objects.PersonsResult;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;
import cs240.trevash.familymap.WebAccess.HttpClient;

import static org.junit.Assert.assertTrue;


public class HttpClientTest {
    private HttpClient mClient = new HttpClient();
    private ConnectDB mDB = new ConnectDB();
    private Connection mConn;
    private String authToken;

    @Before
    public void setup() {
        try {
            mDB.openConnection();
            mConn = mDB.getConn();
            mDB.getClearDB().clearDB(mConn);
            mDB.closeConnection(true);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        try {
            mDB.openConnection();
            mConn = mDB.getConn();
            mDB.getClearDB().clearDB(mConn);
            mDB.closeConnection(true);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerLoginTest() {
        try {
            URL registerUrl = new URL("http://192.168.1.129:8000/user/register");
            RegisterRequest registerRequest = new RegisterRequest("fred","password","ea","fn","ln","M");
            RegisterLoginResult register = mClient.register(registerUrl, registerRequest);
            assertTrue(register.getMessage() == null);

            URL loginUrl = new URL("http://127.0.0.1:8000/user/login");
            LoginRequest loginRequest = new LoginRequest("fred", "password");
            RegisterLoginResult login = mClient.login(loginUrl, loginRequest);
            authToken = login.getCurrAuth().toString();
            assertTrue(register.getMessage() == null);
        }
        catch (MalformedURLException e) {
            Log.e("HttpClientTest", e.getMessage(), e);
        }
    }

    @Test
    public void getPersonsTest() {
        try {
            registerLoginTest();
            URL getPersonsUrl = new URL("http://127.0.0.1:8000/person");
            PersonsResult personsResult = mClient.getPersons(getPersonsUrl, authToken);
            assertTrue(personsResult.getMessage() == null && personsResult.getData().size() > 0);
        }
        catch (MalformedURLException e) {
            Log.e("HttpClientTest", e.getMessage(), e);
        }
    }

    @Test
    public void getEventsTest() {
        try {
            registerLoginTest();
            URL getEventsUrl = new URL("http://127.0.0.1:8000/event");
            EventsResult eventsResult = mClient.getEvents(getEventsUrl, authToken);
            assertTrue(eventsResult.getMessage() == null && eventsResult.getData().size() > 0);
        }
        catch (MalformedURLException e) {
            Log.e("HttpClientTest", e.getMessage(), e);
        }
    }
}
