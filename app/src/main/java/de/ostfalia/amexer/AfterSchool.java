package de.ostfalia.amexer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Activity for afterschool-buttons
 * It show three buttons (bars, clubs, restaurants).
 * By clicking it opens a google-Map in browser or Google-Maps-App
 * @author Lina Tacke
*/
public class AfterSchool extends AppCompatActivity {
    private Button btnPubs;
    private Button btnClubs;
    private Button btnRestaurants;

    /**
     * Initialize the activity
     * @param savedInstanceState the user's current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_school);

        btnPubs = (Button) findViewById(R.id.buttonPubs);
        btnClubs = (Button) findViewById(R.id.buttonClubs);
        btnRestaurants = (Button) findViewById(R.id.buttonRestaurant);

        setActions();
        setActionBar();
    }

    /**
     * Puts an image into the actionbar and removes the appname
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Puts an Image to the Action Bar
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_after_school);
            // Removes the text in Action bar
            actionBar.setDisplayShowTitleEnabled(false);
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }

    /**
     * Sets the actions to the buttons
     * By click it opens Google Maps-App or Google Map in choosen browser with Search-String
     */
    private void setActions() {

        final String barsLink = getString(R.string.barsLink);
        final String clubsLink = getString(R.string.clubsLink);
        final String restaurantsLink = getString(R.string.restaurantsLink);

        btnPubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(barsLink)));
            }
        });

        btnClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(clubsLink)));
            }
        });

        btnRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(restaurantsLink)));
            }
        });
    }
}
