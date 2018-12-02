package cs240.trevash.familymap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cs240.trevash.familymap.Models.DataModel;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String eventId = "eventId";
    public static final String viewablePerson = "personToViewID";

    // TODO: Rename and change types of parameters
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;
    private DataModel mModel = DataModel.getDataModel();
    private LinearLayout mEventDetails;
    private TextView mName;
    private TextView mEventTitle;
    private ImageView mGenderImage;
    private View mMapFragment;
    private List<String> fathersSide = new ArrayList<>();
    private List<String> mothersSide = new ArrayList<>();
    private Person selectedPerson;
    private Event selectedEvent;
    private List<Polyline> lines = new ArrayList<>();


    public MapFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance(String selectedEventId) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(eventId, selectedEventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedEvent = mModel.getEvents().get(getArguments().getString(eventId));
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = view;

        mEventDetails = (LinearLayout) mMapFragment.findViewById(R.id.eventDetails);
        mEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                intent.putExtra(viewablePerson, selectedPerson.getPersonId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_login, menu);

        MenuItem search = menu.findItem(R.id.search);
        Drawable searchIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).colorRes(R.color.white).sizeDp(30);
        search.setIcon(searchIcon);

        MenuItem filter = menu.findItem(R.id.filter);
        Drawable filterIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter).colorRes(R.color.white).sizeDp(30);
        filter.setIcon(filterIcon);

        MenuItem settings = menu.findItem(R.id.settings);
        Drawable settingsIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear).colorRes(R.color.white).sizeDp(30);
        settings.setIcon(settingsIcon);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.filter:
                Intent intent1 = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent1);
                return true;
            case R.id.settings:
                Intent intent2 = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        mGoogleMap = map;

        mModel.filterEvents();
        fathersSide = mModel.getFathersSide();
        mothersSide = mModel.getMothersSide();

        Map<String, Event> Events = mModel.getFilteredEvents();

        for (Map.Entry<String, Event> mapEntry : Events.entrySet()) {
            String id = mapEntry.getKey();
            Event event = mapEntry.getValue();

            Double newLat = Double.parseDouble(event.getLatitude());
            Double newLong = Double.parseDouble(event.getLongitude());
            LatLng newLoc = new LatLng(newLat, newLong);

            switch (event.getEventType()) {
                case "Birth":
                    mGoogleMap.addMarker(new MarkerOptions().position(newLoc).title(event.getEventId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    break;
                case "Marriage":
                    mGoogleMap.addMarker(new MarkerOptions().position(newLoc).title(event.getEventId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    break;
                case "Death":
                    mGoogleMap.addMarker(new MarkerOptions().position(newLoc).title(event.getEventId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    break;
                default:
                    mGoogleMap.addMarker(new MarkerOptions().position(newLoc).title(event.getEventId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    break;
            }
        }

        //TODO: FIGURE THIS OUT
        if (selectedEvent != null) {
            Double selectedLat = Double.parseDouble(selectedEvent.getLatitude());
            Double selectedLong = Double.parseDouble(selectedEvent.getLongitude());
            LatLng selectedLoc = new LatLng(selectedLat, selectedLong);

            setPersonInfo(selectedEvent);
            drawLines(selectedLoc);

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(selectedLoc));
        }

        switch (mModel.getMapType()) {
            case Hybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case Terrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case Satellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            default:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.hideInfoWindow();
                String eventId = marker.getTitle();
                Event selectedEvent = mModel.getEvents().get(eventId);

                Double selectedLat = Double.parseDouble(selectedEvent.getLatitude());
                Double selectedLong = Double.parseDouble(selectedEvent.getLongitude());
                LatLng selectedLoc = new LatLng(selectedLat, selectedLong);

                setPersonInfo(selectedEvent);
                drawLines(selectedLoc);
                return false;
            }
        });
    }

    public void setPersonInfo(Event event) {
        selectedPerson = mModel.getAncestors().get(event.getPersonId());
        Drawable genderIcon;

        if (selectedPerson.getGender().equals("M")) {
            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(40);
        }
        else genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(40);

        mGenderImage = (ImageView) mMapFragment.findViewById(R.id.genderImage);
        mGenderImage.setImageDrawable(genderIcon);

        mName = (TextView) mMapFragment.findViewById(R.id.eventOwner);
        String eventNameString = selectedPerson.getFirstName() + " " + selectedPerson.getLastName();
        mName.setText(eventNameString);

        mEventTitle = (TextView) mMapFragment.findViewById(R.id.eventType_Date);
        String eventTitleString = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ") ";
        mEventTitle.setText(eventTitleString);
    }

    private void drawSpouseLine(LatLng headLocation) {
        String spouseId = selectedPerson.getSpouseId();

        if (spouseId != null && mModel.isSpouseSwitchChecked()) {
            List<Event> events = mModel.getPersonEvents().get(spouseId);
            Event lineToEvent = null;

            for (int i = 0; i < events.size(); i++) {
                Event tempEvent = events.get(i);
                if (lineToEvent != null && mModel.getFilteredEvents().get(tempEvent.getEventId()) != null) {
                    if (tempEvent.getEventType().equals("Birth"))
                        lineToEvent = tempEvent;
                    else if (tempEvent.getEventType().equals("Marriage") && !lineToEvent.getEventType().equals("Birth"))
                        lineToEvent = tempEvent;
                    else if (tempEvent.getEventType().equals("Death") && !lineToEvent.getEventType().equals("Birth") && !lineToEvent.getEventType().equals("Marriage"))
                        lineToEvent = tempEvent;
                } else if (mModel.getFilteredEvents().get(tempEvent.getEventId()) != null)
                    lineToEvent = tempEvent;
            }

            if (lineToEvent != null) {
                Double lat = Double.parseDouble(lineToEvent.getLatitude());
                Double lang = Double.parseDouble(lineToEvent.getLongitude());

                Polyline polyline;

                switch (mModel.getSpouseLineColor()) {
                    case Red:
                        PolylineOptions line = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.female_icon));
                        polyline = mGoogleMap.addPolyline(line);
                        break;
                    case Blue:
                        PolylineOptions line1 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.male_icon));
                        polyline = mGoogleMap.addPolyline(line1);
                        break;
                    case Green:
                        PolylineOptions line2 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.green));
                        polyline = mGoogleMap.addPolyline(line2);
                        break;
                    case Yellow:
                        PolylineOptions line3 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.yellow));
                        polyline = mGoogleMap.addPolyline(line3);
                        break;
                    case Purple:
                        PolylineOptions line4 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.purple));
                        polyline = mGoogleMap.addPolyline(line4);
                        break;
                    default:
                        PolylineOptions line5 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.green));
                        polyline = mGoogleMap.addPolyline(line5);
                        break;
                }

                lines.add(polyline);
            }

        }

    }

    private void drawFamilyTreeLines(LatLng headLocation, String personId, int lineWidth) {

        Map<String, Event> events = mModel.getFilteredEvents();
        List<Event> motherEvents = mModel.getPersonEvents().get(mModel.getAncestors().get(personId).getFatherId());
        List<Event> fatherEvents = mModel.getPersonEvents().get(mModel.getAncestors().get(personId).getMotherId());

        Event chosenMotherEvent = null;
        Event chosenFatherEvent = null;

        if (motherEvents != null) {
            for (Map.Entry<String, Event> mapEntry : events.entrySet()) {
                for (int i = 0; i < motherEvents.size(); i++) {
                    Event event = mapEntry.getValue();

                    if (motherEvents.get(i).getEventId().equals(event.getEventId())) {
                        if (chosenMotherEvent != null && mModel.getFilteredEvents().get(event.getEventId()) != null) {
                            if (event.getEventType().equals("Birth"))
                                chosenMotherEvent = event;
                            else if (event.getEventType().equals("Marriage") && !chosenMotherEvent.getEventType().equals("Birth"))
                                chosenMotherEvent = event;
                            else if (event.getEventType().equals("Death") && !chosenMotherEvent.getEventType().equals("Birth") && !chosenMotherEvent.getEventType().equals("Marriage"))
                                chosenMotherEvent = event;
                        } else if (mModel.getFilteredEvents().get(event.getEventId()) != null)
                            chosenMotherEvent = event;
                    }
                }
            }
        }

        if (chosenMotherEvent != null) {

            Double lat = Double.parseDouble(chosenMotherEvent.getLatitude());
            Double lang = Double.parseDouble(chosenMotherEvent.getLongitude());
            LatLng newStart = new LatLng(lat, lang);

            Polyline polyline;

            switch (mModel.getFamilyLineColor()) {
                case Red:
                    PolylineOptions line = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.female_icon)).width(lineWidth);
                    polyline = mGoogleMap.addPolyline(line);
                    break;
                case Blue:
                    PolylineOptions line1 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.male_icon)).width(lineWidth);
                    polyline = mGoogleMap.addPolyline(line1);
                    break;
                case Green:
                    PolylineOptions line2 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.green)).width(lineWidth);
                    polyline = mGoogleMap.addPolyline(line2);
                    break;
                case Yellow:
                    PolylineOptions line3 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.yellow)).width(lineWidth);
                    polyline = mGoogleMap.addPolyline(line3);
                    break;
                case Purple:
                    PolylineOptions line4 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.purple)).width(lineWidth);
                    polyline = mGoogleMap.addPolyline(line4);
                    break;
                default:
                    PolylineOptions line5 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.green)).width(lineWidth);
                    polyline = mGoogleMap.addPolyline(line5);
                    break;
            }
            lines.add(polyline);
            drawFamilyTreeLines(newStart, chosenMotherEvent.getPersonId(), lineWidth / 2);
        }

        if (fatherEvents != null) {
            for (Map.Entry<String, Event> mapEntry : events.entrySet()) {
                for (int i = 0; i < fatherEvents.size(); i++) {
                    Event event = mapEntry.getValue();

                    if (fatherEvents.get(i).getEventId().equals(event.getEventId())) {
                        if (chosenFatherEvent != null && mModel.getFilteredEvents().get(event.getEventId()) != null) {
                            if (event.getEventType().equals("Birth"))
                                chosenFatherEvent = event;
                            else if (event.getEventType().equals("Marriage") && !chosenFatherEvent.getEventType().equals("Birth"))
                                chosenFatherEvent = event;
                            else if (event.getEventType().equals("Death") && !chosenFatherEvent.getEventType().equals("Birth") && !chosenFatherEvent.getEventType().equals("Marriage"))
                                chosenFatherEvent = event;
                        } else if (mModel.getFilteredEvents().get(event.getEventId()) != null)
                            chosenFatherEvent = event;
                    }
                }
            }
        }

        if (chosenFatherEvent != null) {

            Double lat = Double.parseDouble(chosenFatherEvent.getLatitude());
            Double lang = Double.parseDouble(chosenFatherEvent.getLongitude());
            LatLng newStart = new LatLng(lat, lang);

            Polyline polyline;

            switch (mModel.getFamilyLineColor()) {
                case Red:
                    PolylineOptions line = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.female_icon));
                    polyline = mGoogleMap.addPolyline(line);
                    break;
                case Blue:
                    PolylineOptions line1 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.male_icon));
                    polyline = mGoogleMap.addPolyline(line1);
                    break;
                case Green:
                    PolylineOptions line2 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.green));
                    polyline = mGoogleMap.addPolyline(line2);
                    break;
                case Yellow:
                    PolylineOptions line3 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.yellow));
                    polyline = mGoogleMap.addPolyline(line3);
                    break;
                case Purple:
                    PolylineOptions line4 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.purple));
                    polyline = mGoogleMap.addPolyline(line4);
                    break;
                default:
                    PolylineOptions line5 = new PolylineOptions().add(headLocation, newStart).color(getContext().getResources().getColor(R.color.green));
                    polyline = mGoogleMap.addPolyline(line5);
                    break;
            }
            lines.add(polyline);
            drawFamilyTreeLines(newStart, chosenFatherEvent.getPersonId(), lineWidth / 2);
        }
    }

    private void drawLifeStoryLines(LatLng headLocation) {

        if (mModel.isLifeStorySwitchChecked()) {
            Map<String, Event> events = mModel.getFilteredEvents();
            List<Event> personEvents = mModel.getPersonEvents().get(selectedPerson.getPersonId());

            for (Map.Entry<String, Event> mapEntry : events.entrySet()) {
                for (int i = 0; i < personEvents.size(); i++) {
                    Event event = mapEntry.getValue();

                    if (personEvents.get(i).getEventId().equals(event.getEventId())) {

                        Double lat = Double.parseDouble(event.getLatitude());
                        Double lang = Double.parseDouble(event.getLongitude());

                        Polyline polyline;

                        switch (mModel.getLifeStoryLineColor()) {
                            case Red:
                                PolylineOptions line = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.female_icon));
                                polyline = mGoogleMap.addPolyline(line);
                                break;
                            case Blue:
                                PolylineOptions line1 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.male_icon));
                                polyline = mGoogleMap.addPolyline(line1);
                                break;
                            case Green:
                                PolylineOptions line2 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.green));
                                polyline = mGoogleMap.addPolyline(line2);
                                break;
                            case Yellow:
                                PolylineOptions line3 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.yellow));
                                polyline = mGoogleMap.addPolyline(line3);
                                break;
                            case Purple:
                                PolylineOptions line4 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.purple));
                                polyline = mGoogleMap.addPolyline(line4);
                                break;
                            default:
                                PolylineOptions line5 = new PolylineOptions().add(headLocation, new LatLng(lat, lang)).color(getContext().getResources().getColor(R.color.green));
                                polyline = mGoogleMap.addPolyline(line5);
                                break;
                        }
                        lines.add(polyline);
                    }
                }
            }
        }


    }

    private void drawLines(LatLng headLocation) {
        for (Polyline polyline : lines) {
            polyline.remove();
        }

        drawSpouseLine(headLocation);

        if (mModel.isFamilySwitchChecked()) {
            int lineWidth = 10;
            drawFamilyTreeLines(headLocation, selectedPerson.getPersonId(), lineWidth);
        }

        drawLifeStoryLines(headLocation);
    }

}
