package cs240.trevash.familymap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs240.trevash.familymap.Models.DataModel;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;

public class PersonActivity extends AppCompatActivity {

    public static final String viewablePerson = "personToViewID";
    public static final String eventId = "eventId";

    private DataModel mModel = DataModel.getDataModel();
    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private RecyclerView lifeEventsRecyclerView;
    private RecyclerView familyRecylclerView;

    private DataModel mDataModel = DataModel.getDataModel();
    private Person selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Iconify.with(new FontAwesomeModule());

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPerson = (Person) mDataModel.getAncestors().get(b.getString(viewablePerson));
        }

        firstName = (TextView) findViewById(R.id.first_name);
        firstName.setText(selectedPerson.getFirstName());

        lastName = (TextView) findViewById(R.id.last_name);
        lastName.setText(selectedPerson.getLastName());

        gender = (TextView) findViewById(R.id.gender);
        if (selectedPerson.getGender().equals("M")) gender.setText("Male");
        else gender.setText("Female");

        lifeEventsRecyclerView = (RecyclerView) findViewById(R.id.life_events_recycler_view);
        lifeEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        lifeEventExpandableAdapter lifeEventExpandableAdapter = new lifeEventExpandableAdapter(this, generateLifeEvents());
        lifeEventExpandableAdapter.setCustomParentAnimationViewId(R.id.life_events_carrot_parent);
        lifeEventExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        lifeEventExpandableAdapter.setParentAndIconExpandOnClick(true);

        lifeEventsRecyclerView.setAdapter(lifeEventExpandableAdapter);


        familyRecylclerView = (RecyclerView) findViewById(R.id.family_recycler_view);
        familyRecylclerView.setLayoutManager(new LinearLayoutManager(this));

        FamilyExpandableAdapter familyExpandableAdapter = new FamilyExpandableAdapter(this, generateFamily());
        familyExpandableAdapter.setCustomParentAnimationViewId(R.id.family_carrot_parent);
        familyExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        familyExpandableAdapter.setParentAndIconExpandOnClick(true);

        familyRecylclerView.setAdapter(familyExpandableAdapter);

    }

    private String getRelationship(Person person) {
        if (selectedPerson.getMotherId() != null && selectedPerson.getMotherId().equals(person.getPersonId())) return "Mother";
        else if (selectedPerson.getFatherId() != null && selectedPerson.getFatherId().equals(person.getPersonId())) return "Father";
        else if (selectedPerson.getSpouseId() != null && selectedPerson.getSpouseId().equals(person.getPersonId())) return "Spouse";
        else return "Child";
    }



    private ArrayList<ParentObject> generateLifeEvents() {
        List<Event> events = mModel.getPersonEvents().get(selectedPerson.getPersonId());
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        ParentObject parentObject = new lifeEventObject();
        ArrayList<Object> childList = new ArrayList<>();

        childList.addAll(events);
        parentObject.setChildObjectList(childList);
        parentObjects.add(parentObject);

        return parentObjects;
    }

    private class lifeEventParentHolder extends ParentViewHolder {

        private TextView titleTextView;
        private ImageButton dropdown;

        public lifeEventParentHolder(View itemView) {
            super(itemView);

            dropdown = (ImageButton) itemView.findViewById(R.id.life_events_carrot_parent);
            titleTextView = (TextView) itemView.findViewById(R.id.life_events_label_parent);
        }
    }

    private class lifeEventChildHolder extends ChildViewHolder {

        private ImageView mMarkerImageView;
        private TextView mTypeLocationTextView;
        private TextView mNameTextView;
        private LinearLayout clickLayout;
        private Event currEvent;

        public lifeEventChildHolder(View itemView) {
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

    private class lifeEventObject implements ParentObject {

        private List<Object> mEvents;

        @Override
        public List<Object> getChildObjectList() {
            return mEvents;
        }

        @Override
        public void setChildObjectList(List<Object> events) {
            mEvents = events;
        }
    }

    private class lifeEventExpandableAdapter extends ExpandableRecyclerAdapter<lifeEventParentHolder, lifeEventChildHolder> {

        LayoutInflater mInflater;

        public lifeEventExpandableAdapter(Context context, ArrayList<ParentObject> lifeEvents) {
            super(context, lifeEvents);

            mInflater = LayoutInflater.from(context);

        }

        @Override
        public lifeEventParentHolder onCreateParentViewHolder(ViewGroup viewGroup) {
            View view = mInflater.inflate(R.layout.event_parent, viewGroup, false);
            return new lifeEventParentHolder(view);
        }

        @Override
        public lifeEventChildHolder onCreateChildViewHolder(ViewGroup viewGroup) {
            View view = mInflater.inflate(R.layout.event_child, viewGroup, false);
            return new lifeEventChildHolder(view);
        }

        @Override
        public void onBindParentViewHolder(lifeEventParentHolder lifeEventParentHolder, int i, Object parentObject) {
            //Event event = (Event) parentObject;
            lifeEventParentHolder.titleTextView.setText("Life Events");
        }

        public void onBindChildViewHolder(lifeEventChildHolder lifeEventChildHolder, int i, Object childObject) {
            final Event event = (Event) childObject;

            Drawable marker = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.gray_icon).sizeDp(40);
            lifeEventChildHolder.mMarkerImageView.setImageDrawable(marker);
            String eventTitleString = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ") ";
            lifeEventChildHolder.mTypeLocationTextView.setText(eventTitleString);
            String eventNameString = selectedPerson.getFirstName() + " " + selectedPerson.getLastName();
            lifeEventChildHolder.mNameTextView.setText(eventNameString);
            lifeEventChildHolder.currEvent = event;
        }
    }



    private ArrayList<ParentObject> generateFamily() {
        List<Person> family = new ArrayList<>();

        if (selectedPerson.getSpouseId() != null) {
            family.add(mModel.getAncestors().get(selectedPerson.getSpouseId()));
        }
        if (selectedPerson.getMotherId() != null) {
            family.add(mModel.getAncestors().get(selectedPerson.getMotherId()));

        }
        if (selectedPerson.getFatherId() != null) {
            family.add(mModel.getAncestors().get(selectedPerson.getFatherId()));
        }

        for (Map.Entry<String, Person> mapEntry : mModel.getAncestors().entrySet()) {
            Person person = mapEntry.getValue();
            if ((person.getFatherId() != null && person.getFatherId().equals(selectedPerson.getPersonId())) || (person.getMotherId() != null && person.getMotherId().equals(selectedPerson.getPersonId()))) {
                family.add(person);
            }
        }

        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        ParentObject parentObject = new FamilyObject();
        ArrayList<Object> childList = new ArrayList<>();

        childList.addAll(family);
        parentObject.setChildObjectList(childList);
        parentObjects.add(parentObject);

        return parentObjects;
    }

    private class FamilyParentHolder extends ParentViewHolder {

        private TextView titleTextView;
        private ImageButton dropdown;

        public FamilyParentHolder(View itemView) {
            super(itemView);

            dropdown = (ImageButton) itemView.findViewById(R.id.family_carrot_parent);
            titleTextView = (TextView) itemView.findViewById(R.id.family_label_parent);
        }
    }

    private class FamilyChildHolder extends ChildViewHolder {

        private ImageView mMarkerImageView;
        private TextView mName;
        private TextView mRelationship;
        private String personId;
        private LinearLayout clickLayout;

        public FamilyChildHolder(View itemView) {
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
            mRelationship = (TextView) itemView.findViewById(R.id.family_relationship);
        }
    }

    private class FamilyExpandableAdapter extends ExpandableRecyclerAdapter<FamilyParentHolder, FamilyChildHolder> {

        LayoutInflater mInflater;

        public FamilyExpandableAdapter(Context context, ArrayList<ParentObject> members) {
            super(context, members);

            mInflater = LayoutInflater.from(context);

        }

        @Override
        public FamilyParentHolder onCreateParentViewHolder(ViewGroup viewGroup) {
            View view = mInflater.inflate(R.layout.family_parent, viewGroup, false);
            return new FamilyParentHolder(view);
        }

        @Override
        public FamilyChildHolder onCreateChildViewHolder(ViewGroup viewGroup) {
            View view = mInflater.inflate(R.layout.family_child, viewGroup, false);
            return new FamilyChildHolder(view);
        }

        @Override
        public void onBindParentViewHolder(FamilyParentHolder familyParentHolder, int i, Object parentObject) {
            familyParentHolder.titleTextView.setText("Family");
        }

        public void onBindChildViewHolder(FamilyChildHolder familyChildHolder, int i, Object childObject) {
            Person person = (Person) childObject;
            String personId;

            Drawable marker;
            personId = person.getPersonId();

            if (person.getGender().equals("M")) {
                marker = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(40);
            }
            else {
                marker = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(40);
            }
            familyChildHolder.mMarkerImageView.setImageDrawable(marker);

            String personName = person.getFirstName() + " " + person.getLastName();
            familyChildHolder.mName.setText(personName);

            String relationship = getRelationship(person);
            familyChildHolder.mRelationship.setText(relationship);

            familyChildHolder.personId = personId;

        }
    }

    private class FamilyObject implements ParentObject {

        private List<Object> mMembers;

        @Override
        public List<Object> getChildObjectList() {
            return mMembers;
        }

        @Override
        public void setChildObjectList(List<Object> members) {
            mMembers = members;
        }
    }
}