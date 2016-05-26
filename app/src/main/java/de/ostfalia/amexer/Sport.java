package de.ostfalia.amexer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.ostfalia.amexer.entries.CSVReader;

/**
 * Activity for showing the sportcourses from ostfalia in a ListView
 * By clicking an element (sportcourse) the browser will be startet with the e-filling
 * The class gets the sportcourses form the sports_data.csv
 * @author Lina Tacke
 */
public class Sport extends AppCompatActivity {
    private ListView sportsListView;
    private List<String> sportsList;
    private InputStream inputStream;

    /**
     * Initialize the activity
     * @param savedInstanceState the user's current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gets the csv
        try {
            inputStream = this.getAssets().open(getString(R.string.sports_data_csv));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        fillList();
        sportsListView = (ListView) findViewById(R.id.sportListView);
        setActions();
        setActionBar();
    }

    /**
     * Puts an image into the actionbar and removes the appname
     */
    private void setActionBar() {
        //Puts an Image to the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_sport);
            // removes the Text from action-bar
            actionBar.setDisplayShowTitleEnabled(false);
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }

    /**
     * Fills the list with data
     */
    private void fillList() {
        sportsList = new ArrayList<>(new CSVReader(inputStream).getData());

        ArrayAdapter<String> sportslistAdapter = new ArrayAdapter<>(
                this,                           // This Activity
                R.layout.list_item_sportslist,  // ID from XML-Layout-Data
                R.id.item_list_textview,        // ID from TextViews
                sportsList);

        ListView sportslistListView = (ListView) this.findViewById(R.id.sportListView);
        assert sportslistListView != null;
        sportslistListView.setAdapter(sportslistAdapter);
        sportslistAdapter.notifyDataSetChanged();
    }

    /**
     * Creates the Link by clicking on list-item(sportcourse)
     */
    private void setActions() {
        sportsListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Uri uri = sporttypeToUri(sportsList.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    /**
     * Convert a sporttype to a uri
     * @param s string to convert
     * @return uri
     */
    private Uri sporttypeToUri(String s) {
        String webUri = getString(R.string.sportsLink);
        return Uri.parse(webUri + s.replace(" ", "_")
                .replace("ä", "ae")
                .replace("ö", "oe")
                .replace("ü", "ue")
                .replace("ß", "ss")
                .replace("/", "_")
                .replace("(", "_")
                .replace(")", "_")
                + ".html");
    }
}
