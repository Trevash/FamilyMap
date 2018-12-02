package cs240.trevash.familymap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cs240.trevash.familymap.Models.DataModel;
import cs240.trevash.familymap.shared.Models.Event;

public class EventActivity extends AppCompatActivity {

    public static final String eventId = "eventId";

    private Event thisEvent;
    private DataModel mModel = DataModel.getDataModel();

    private FragmentManager fm = this.getSupportFragmentManager();
    private MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.eventMapFrameLayout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            thisEvent = mModel.getEvents().get(b.getString(eventId));
        }

        mapFragment = MapFragment.newInstance(thisEvent.getEventId());
        fm.beginTransaction().add(R.id.eventMapFrameLayout, mapFragment).commit();
    }

}
