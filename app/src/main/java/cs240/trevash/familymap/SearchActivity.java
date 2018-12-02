package cs240.trevash.familymap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs240.trevash.familymap.Models.DataModel;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;

public class SearchActivity extends AppCompatActivity {

    public static final String eventId = "eventId";
    public static final String viewablePerson = "personToViewID";

    private DataModel mModel = DataModel.getDataModel();
    private SearchView searchView;
    private RecyclerView searchValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.search_for_stuff);

        SearchAdapter searchAdapter = new SearchAdapter(mModel.getFilteredEvents(), mModel.getAncestors());

        searchValues = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchValues.setLayoutManager(new LinearLayoutManager(this));
        searchValues.setAdapter(searchAdapter);

    }

    public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Object> mObjects = new ArrayList<>();

        SearchAdapter(Map<String, Event> eventMap, Map<String, Person> ancestors) {
            for (Map.Entry<String, Person> personEntry : ancestors.entrySet()) {
                Person person = personEntry.getValue();
                mObjects.add(person);
            }
            for (Map.Entry<String, Event> eventEntry : eventMap.entrySet()) {
                Event event = eventEntry.getValue();
                mObjects.add(event);
            }
        }

        public class EventHolder extends RecyclerView.ViewHolder {

            private ImageView mMarkerImageView;
            private TextView mTypeLocationTextView;
            private TextView mNameTextView;
            private LinearLayout clickLayout;
            private Event currEvent;

            EventHolder(View itemView) {
                super(itemView);

                clickLayout = (LinearLayout) itemView.findViewById(R.id.life_event_value);
                clickLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                        intent.putExtra(eventId, currEvent.getEventId());
                        startActivity(intent);
                    }
                });
                mMarkerImageView = (ImageView) itemView.findViewById(R.id.event_marker);
                mTypeLocationTextView = (TextView) itemView.findViewById(R.id.event_type_location);
                mNameTextView = (TextView) itemView.findViewById(R.id.event_name);

            }
        }

        public class PersonHolder extends RecyclerView.ViewHolder {

            private ImageView mMarkerImageView;
            private TextView mName;
            private String personId;
            private LinearLayout clickLayout;

            PersonHolder(View itemView) {
                super(itemView);

                clickLayout = (LinearLayout) itemView.findViewById(R.id.family_value);
                clickLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
                        intent.putExtra(viewablePerson, personId);
                        startActivity(intent);
                    }
                });
                mMarkerImageView = (ImageView) itemView.findViewById(R.id.gender_image);
                mName = (TextView) itemView.findViewById(R.id.family_name);
            }
        }

        @Override
        public int getItemViewType(int position) {
            Object object = mObjects.get(position);
            if (object.getClass() == Event.class) {
                return 0;
            }
            else if (object.getClass() == Person.class) {
                return 2;
            }
            return 20;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0:
                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.event_child, parent, false);
                    return new EventHolder(view);
                case 2:
                    View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.family_child, parent, false);
                    return new PersonHolder(view1);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    EventHolder eventHolder = (EventHolder) holder;
                    Event event = (Event) mObjects.get(position);
                    Person eventPerson =  mModel.getAncestors().get(event.getPersonId());

                    Drawable marker = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.gray_icon).sizeDp(40);
                    eventHolder.mMarkerImageView.setImageDrawable(marker);
                    String eventTitleString = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ") ";
                    eventHolder.mTypeLocationTextView.setText(eventTitleString);
                    String eventNameString = eventPerson.getFirstName() + " " + eventPerson.getLastName();
                    eventHolder.mNameTextView.setText(eventNameString);
                    eventHolder.currEvent = event;
                    break;
                case 2:
                    PersonHolder personHolder = (PersonHolder) holder;
                    Person person = (Person) mObjects.get(position);
                    String personId;

                    Drawable marker1;
                    personId = person.getPersonId();

                    if (person.getGender().equals("M")) {
                        marker1 = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(40);
                    }
                    else {
                        marker1 = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(40);
                    }
                    personHolder.mMarkerImageView.setImageDrawable(marker1);

                    String personName = person.getFirstName() + " " + person.getLastName();
                    personHolder.mName.setText(personName);

                    personHolder.personId = personId;
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mObjects.size();
        }
    }

}
