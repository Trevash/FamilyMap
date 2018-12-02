package cs240.trevash.familymap;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs240.trevash.familymap.Models.DataModel;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.EventsResult;
import cs240.trevash.familymap.shared.Objects.PersonsResult;

public class MainActivity extends AppCompatActivity implements LoginFragment.ContextInfo {

    private FragmentManager fm = this.getSupportFragmentManager();
    private LoginFragment loginFragment = (LoginFragment) fm.findFragmentById(R.id.loginFrameLayout);
    private MapFragment mMapFragment = (MapFragment) fm.findFragmentById(R.id.mapFrameLayout);
    private DataModel mModel = DataModel.getDataModel();
    private boolean personInfoReturn = false;
    private boolean eventInfoReturn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());

        if (mModel.getUser() != null) {
            if (mMapFragment == null) {
                mMapFragment = new MapFragment();
            }
            fm.beginTransaction().add(R.id.mapFrameLayout, mMapFragment).commit();
        }
        else {
            if (loginFragment == null) {
                loginFragment = new LoginFragment();
            }
            fm.beginTransaction().add(R.id.loginFrameLayout, loginFragment).commit();
        }
    }

    public void personInfoReturn(PersonsResult ancestors) {

        Map<String, Person> mappedAncestors = new HashMap<>();
        for (int i = 0; i < ancestors.getData().size(); i++) {
            mappedAncestors.put(ancestors.getData().get(i).getPersonId(), ancestors.getData().get(i));
        }

        mModel.setAncestors(mappedAncestors);
        personInfoReturn = true;

        if (loginFragment != null && eventInfoReturn) {
            fm.beginTransaction().remove(loginFragment).commit();
            if (mMapFragment == null) {
                mMapFragment = new MapFragment();
            }
            fm.beginTransaction().add(R.id.mapFrameLayout, mMapFragment).commit();
        }
    }

    public void eventInfoReturn(EventsResult events) {

        Map<String, ArrayList<Event>> mappedEventsToPerson = new HashMap<>();
        Map<String, Event> eventsMap = new HashMap<>();

        for (int i = 0; i < events.getData().size(); i++) {
            if (mappedEventsToPerson.get(events.getData().get(i).getPersonId()) != null) {
                ArrayList<Event> individualPersonEvents = mappedEventsToPerson.get(events.getData().get(i).getPersonId());
                individualPersonEvents.add(events.getData().get(i));
            }
            else {
                ArrayList<Event> firstEvent = new ArrayList<>();
                firstEvent.add(events.getData().get(i));
                mappedEventsToPerson.put(events.getData().get(i).getPersonId(), firstEvent);
            }
            eventsMap.put(events.getData().get(i).getEventId(), events.getData().get(i));
        }

        mModel.setPersonEvents(mappedEventsToPerson);
        mModel.setEvents(eventsMap);

        eventInfoReturn = true;

        if (loginFragment != null && personInfoReturn) {
            fm.beginTransaction().remove(loginFragment).commit();
            if (mMapFragment == null) {
                mMapFragment = new MapFragment();
            }
            fm.beginTransaction().add(R.id.mapFrameLayout, mMapFragment).commit();
        }
    }

}
