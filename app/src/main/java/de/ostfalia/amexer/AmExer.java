package de.ostfalia.amexer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Activity with buttons for app-categories
 * By clicking a button it shows a new activity
 * @author Lina Tacke
 */
public class AmExer extends AppCompatActivity {
    private ImageButton food_button;
    private ImageButton maps_button;
    private ImageButton sport_button;
    private ImageButton library_button;
    private ImageButton holidays_button;
    private ImageButton profs_button;
    private ImageButton office_button;
    private ImageButton data_center_button;
    private ImageButton after_school_button;

    /**
     * Initialize the activity
     * @param savedInstanceState the user's current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checks if the display is in landscape or portrait orientation and shows the image
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_am_exer_landscape);
        } else {
            setContentView(R.layout.activity_am_exer_portrait);
        }

        initActivityObjects();
        setActions();
    }

    /**
     * Initialize buttons
     */
    private void initActivityObjects() {
        food_button = (ImageButton) findViewById(R.id.food_button);
        maps_button = (ImageButton) findViewById(R.id.maps_button);
        sport_button = (ImageButton) findViewById(R.id.sport_button);
        library_button = (ImageButton) findViewById(R.id.library_button);
        holidays_button = (ImageButton) findViewById(R.id.holidays_button);
        office_button = (ImageButton) findViewById(R.id.office_button);
        data_center_button = (ImageButton) findViewById(R.id.data_center_button);
        after_school_button = (ImageButton) findViewById(R.id.after_school_button);
        profs_button = (ImageButton) findViewById(R.id.profs_button);
    }

    /**
     * Set actions on buttons
     */
    public void setActions() {

        food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(AmExer.this, ChooseCampus.class));
            }
        });
        maps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, ChooseCampusMaps.class));
            }
        });
        sport_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, Sport.class));
            }
        });

        library_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, Library.class));
            }
        });

        holidays_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, Holidays.class));
            }
        });

        profs_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, Profs.class));
            }
        });

        office_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, Offices.class));
            }
        });

        data_center_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, DataCenter.class));
            }
        });

        after_school_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(AmExer.this, AfterSchool.class));
            }
        });
    }
}
