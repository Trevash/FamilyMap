package cs240.trevash.familymap;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import cs240.trevash.familymap.Models.DataModel;

public class FilterActivity extends AppCompatActivity {

    private Switch BirthSwitch;
    private Switch MarriageSwitch;
    private Switch DeathSwitch;
    private Switch fathersSwitch;
    private Switch mothersSwitch;
    private Switch maleSwitch;
    private Switch femaleSwitch;
    private DataModel mModel = DataModel.getDataModel();
    private MenuItem toTopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        BirthSwitch = (Switch) findViewById(R.id.BirthSwitch);
        if (mModel.isBirthEventsSelected()) BirthSwitch.setChecked(true);
        BirthSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.isBirthEventsSelected()) {
                    BirthSwitch.setChecked(false);
                    mModel.setBirthEventsSelected(false);
                }
                else {
                    BirthSwitch.setChecked(true);
                    mModel.setBirthEventsSelected(true);
                }
            }
        });

        MarriageSwitch = (Switch) findViewById(R.id.MarriageSwitch);
        if (mModel.isMarriageEventsSelected()) MarriageSwitch.setChecked(true);
        MarriageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.isMarriageEventsSelected()) {
                    MarriageSwitch.setChecked(false);
                    mModel.setMarriageEventsSelected(false);
                }
                else {
                    MarriageSwitch.setChecked(true);
                    mModel.setMarriageEventsSelected(true);
                }
            }
        });

        DeathSwitch = (Switch) findViewById(R.id.DeathSwitch);
        if (mModel.isDeathEventsSelected()) DeathSwitch.setChecked(true);
        DeathSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.isDeathEventsSelected()) {
                    DeathSwitch.setChecked(false);
                    mModel.setDeathEventsSelected(false);
                }
                else {
                    DeathSwitch.setChecked(true);
                    mModel.setDeathEventsSelected(true);
                }
            }
        });

        fathersSwitch = (Switch) findViewById(R.id.fathersSwitch);
        if (mModel.isFathersSideSelected()) fathersSwitch.setChecked(true);
        fathersSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.isFathersSideSelected()) {
                    fathersSwitch.setChecked(false);
                    mModel.setFathersSideSelected(false);
                }
                else {
                    fathersSwitch.setChecked(true);
                    mModel.setFathersSideSelected(true);
                }
            }
        });

        mothersSwitch = (Switch) findViewById(R.id.mothersSwitch);
        if (mModel.isMothersSideSelected()) mothersSwitch.setChecked(true);
        mothersSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.isMothersSideSelected()) {
                    mothersSwitch.setChecked(false);
                    mModel.setMothersSideSelected(false);
                }
                else {
                    mothersSwitch.setChecked(true);
                    mModel.setMothersSideSelected(true);
                }
            }
        });

        maleSwitch = (Switch) findViewById(R.id.maleSwitch);
        if (mModel.isMaleEventsSelected()) maleSwitch.setChecked(true);
        maleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.isMaleEventsSelected()) {
                    maleSwitch.setChecked(false);
                    mModel.setMaleEventsSelected(false);
                }
                else {
                    maleSwitch.setChecked(true);
                    mModel.setMaleEventsSelected(true);
                }
            }
        });

        femaleSwitch = (Switch) findViewById(R.id.femaleSwitch);
        if (mModel.isFemaleEventsSelected()) femaleSwitch.setChecked(true);
        femaleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.isFemaleEventsSelected()) {
                    femaleSwitch.setChecked(false);
                    mModel.setFemaleEventsSelected(false);
                }
                else {
                    femaleSwitch.setChecked(true);
                    mModel.setFemaleEventsSelected(true);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toTopButton = menu.findItem(R.id.homeAsUp);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
