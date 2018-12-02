package cs240.trevash.familymap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs240.trevash.familymap.Models.DataModel;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.EventsResult;
import cs240.trevash.familymap.shared.Objects.PersonsResult;
import cs240.trevash.familymap.WebAccess.EventsDataTask;
import cs240.trevash.familymap.WebAccess.PersonsDataTask;

public class SettingsActivity extends AppCompatActivity implements PersonsDataTask.Context, EventsDataTask.Context {

    private static final String SETTINGS_TAG = "SETTINGS ACTIVITY";

    private DataModel model = DataModel.getDataModel();

    private Spinner lifeStoryLineColorSpinner;
    private Switch lifeStorySwitch;
    private Spinner familyLineColorSpinner;
    private Switch familySwitch;
    private Spinner spouseLineColorSpinner;
    private Switch spouseSwitch;
    private Spinner mapTypeSpinner;
    private LinearLayout resyncLayout;
    private LinearLayout logoutLayout;
    private boolean personInfoReturn = false;
    private boolean eventInfoReturn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lifeStoryLineColorSpinner = (Spinner) findViewById(R.id.life_story_spinner);
        ArrayAdapter<CharSequence> lifeStoryAdapter = ArrayAdapter.createFromResource(this, R.array.colorSpinner, android.R.layout.simple_spinner_item);
        lifeStoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lifeStoryLineColorSpinner.setAdapter(lifeStoryAdapter);
        lifeStoryLineColorSpinner.setSelection(model.getLifeStoryLineColor().ordinal());
        lifeStoryLineColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        model.setLifeStoryLineColor(DataModel.LineColor.Red);
                        break;
                    case 1:
                        model.setLifeStoryLineColor(DataModel.LineColor.Green);
                        break;
                    case 2:
                        model.setLifeStoryLineColor(DataModel.LineColor.Blue);
                        break;
                    case 3:
                        model.setLifeStoryLineColor(DataModel.LineColor.Yellow);
                        break;
                    case 4:
                        model.setLifeStoryLineColor(DataModel.LineColor.Purple);
                        break;
                    default:
                        Log.e(SETTINGS_TAG, "Selected an unselectable Item?!");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        lifeStorySwitch = (Switch) findViewById(R.id.life_story_switch);
        lifeStorySwitch.setChecked(model.isLifeStorySwitchChecked());
        lifeStorySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isLifeStorySwitchChecked()) {
                    model.setLifeStorySwitchChecked(false);
                    lifeStorySwitch.setChecked(false);
                }
                else {
                    model.setLifeStorySwitchChecked(true);
                    lifeStorySwitch.setChecked(true);
                }
            }
        });

        familyLineColorSpinner = (Spinner) findViewById(R.id.family_tree_spinner);
        ArrayAdapter<CharSequence> familyLineColorAdapter = ArrayAdapter.createFromResource(this, R.array.colorSpinner, android.R.layout.simple_spinner_item);
        familyLineColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyLineColorSpinner.setAdapter(familyLineColorAdapter);
        familyLineColorSpinner.setSelection(model.getFamilyLineColor().ordinal());
        familyLineColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        model.setFamilyLineColor(DataModel.LineColor.Red);
                        break;
                    case 1:
                        model.setFamilyLineColor(DataModel.LineColor.Green);
                        break;
                    case 2:
                        model.setFamilyLineColor(DataModel.LineColor.Blue);
                        break;
                    case 3:
                        model.setFamilyLineColor(DataModel.LineColor.Yellow);
                        break;
                    case 4:
                        model.setFamilyLineColor(DataModel.LineColor.Purple);
                        break;
                    default:
                        Log.e(SETTINGS_TAG, "Selected an unselectable Item?!");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        familySwitch = (Switch) findViewById(R.id.family_tree_switch);
        familySwitch.setChecked(model.isFamilySwitchChecked());
        familySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isFamilySwitchChecked()) {
                    model.setFamilySwitchChecked(false);
                    familySwitch.setChecked(false);
                }
                else {
                    model.setFamilySwitchChecked(true);
                    familySwitch.setChecked(true);
                }
            }
        });

        spouseLineColorSpinner = (Spinner) findViewById(R.id.spouse_color_spinner);
        ArrayAdapter<CharSequence> spouseLineColorAdapter = ArrayAdapter.createFromResource(this, R.array.colorSpinner, android.R.layout.simple_spinner_item);
        spouseLineColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spouseLineColorSpinner.setAdapter(spouseLineColorAdapter);
        spouseLineColorSpinner.setSelection(model.getSpouseLineColor().ordinal());
        spouseLineColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        model.setSpouseLineColor(DataModel.LineColor.Red);
                        break;
                    case 1:
                        model.setSpouseLineColor(DataModel.LineColor.Green);
                        break;
                    case 2:
                        model.setSpouseLineColor(DataModel.LineColor.Blue);
                        break;
                    case 3:
                        model.setSpouseLineColor(DataModel.LineColor.Yellow);
                        break;
                    case 4:
                        model.setSpouseLineColor(DataModel.LineColor.Purple);
                        break;
                    default:
                        Log.e(SETTINGS_TAG, "Selected an unselectable Item?!");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spouseSwitch = (Switch) findViewById(R.id.spouse_color_switch);
        spouseSwitch.setChecked(model.isSpouseSwitchChecked());
        spouseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isSpouseSwitchChecked()) {
                    model.setSpouseSwitchChecked(false);
                    spouseSwitch.setChecked(false);
                }
                else {
                    model.setSpouseSwitchChecked(true);
                    spouseSwitch.setChecked(true);
                }
            }
        });

        mapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);
        ArrayAdapter<CharSequence> mapTypeAdapter = ArrayAdapter.createFromResource(this, R.array.typeSpinner, android.R.layout.simple_spinner_item);
        mapTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(mapTypeAdapter);
        mapTypeSpinner.setSelection(model.getMapType().ordinal());
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        model.setSelectedMapType(DataModel.MapType.Normal);
                        break;
                    case 1:
                        model.setSelectedMapType(DataModel.MapType.Hybrid);
                        break;
                    case 2:
                        model.setSelectedMapType(DataModel.MapType.Satellite);
                        break;
                    case 3:
                        model.setSelectedMapType(DataModel.MapType.Terrain);
                        break;
                    default:
                        Log.e(SETTINGS_TAG, "Selected an unselectable Item?!");
                }
                mapTypeSpinner.setSelection(model.getMapType().ordinal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        resyncLayout = (LinearLayout) findViewById(R.id.resync_linear_layout);
        resyncLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.clean();
                refreshInfo();
            }
        });

        logoutLayout = (LinearLayout) findViewById(R.id.logout_linear_layout);
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.wipe();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }



    private void refreshInfo() {

        PersonsDataTask personsDataTask = new PersonsDataTask(this, model.getAuthToken().toString());
        EventsDataTask eventsDataTask = new EventsDataTask(this, model.getAuthToken().toString());

        try {
            personsDataTask.execute(new URL("http://" + model.getHost() + ":" + model.getPort() + "/person"));
            eventsDataTask.execute(new URL("http://" + model.getHost() + ":" + model.getPort() + "/event"));
        }
        catch (MalformedURLException e) {
            Log.e(SETTINGS_TAG, e.getMessage(), e);
            Toast.makeText(getApplicationContext(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void personInfo(PersonsResult ancestors) {
        Map<String, Person> mappedAncestors = new HashMap<>();

        for (int i = 0; i < ancestors.getData().size(); i++) {
            mappedAncestors.put(ancestors.getData().get(i).getPersonId(), ancestors.getData().get(i));
        }

        model.setAncestors(mappedAncestors);
        personInfoReturn = true;

        Toast.makeText(this, R.string.person_data_gathered, Toast.LENGTH_SHORT).show();

        if (eventInfoReturn) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void eventInfo(EventsResult events) {
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

        model.setPersonEvents(mappedEventsToPerson);
        model.setEvents(eventsMap);
        eventInfoReturn = true;

        Toast.makeText(this, R.string.event_data_gathered, Toast.LENGTH_SHORT).show();

        if (personInfoReturn) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}
