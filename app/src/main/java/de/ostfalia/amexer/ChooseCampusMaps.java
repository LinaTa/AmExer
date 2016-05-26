package de.ostfalia.amexer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Activity for choosing campus (exer or salzdahlumer)
 * It shows two buttons (exer-campus and salzdahlumer-campus)
 * By Clicking a button it opens the map of the choosen campus.
 * The class gets the informations of the map form the campus_salzdahlumer_data.csv
 * @author Lina Tacke
 */
public class ChooseCampusMaps extends AppCompatActivity {
    private Button campusAmExer;
    private Button campusSalzdahlumer;

    /**
     * Initialize the activity
     * @param savedInstanceState the user's current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_campus_maps);

        campusAmExer = (Button) findViewById(R.id.campusExer);
        campusSalzdahlumer = (Button) findViewById(R.id.campusSalzdahlumer);
        setActions();
        setActionBar();
    }

    /**
     * Puts an image into the actionbar and removes the appname
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // puts an image into the actionbar
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_maps);
            // removes the text in action-bar
            actionBar.setDisplayShowTitleEnabled(false);
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }

    /**
     * Sets the action by button-click
     */
    private void setActions(){
        campusAmExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseCampusMaps.this, MapExer.class));
            }
        });

        campusSalzdahlumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseCampusMaps.this, MapSalzdahlumer.class));
            }
        });
    }
}
