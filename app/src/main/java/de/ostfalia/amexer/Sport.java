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

import de.ostfalia.amexer.entries.CSVReaderSport;

public class Sport extends AppCompatActivity {
    private ListView sportsListView;
    private ArrayAdapter<String> sportslistAdapter;
    private List<String> sportsList;
    private InputStream iS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Holt sich die csv-Datei
        try {
            iS = this.getAssets().open("sports.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        fillList();
        sportsListView = (ListView) findViewById(R.id.sportListView);
        setActions();

        //Puts an Image to the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_sport);
            actionBar.setDisplayShowTitleEnabled(false); // entfernt den text von der Action bar
            Log.i("FoodAmExer", " action bar");
        } else {
            Log.i("FoodAmExer", "no action bar");
        }
    }

    /**
     * Initialisiert und füllt die Liste
     */
    private void fillList() {
        sportsList = new ArrayList<>(new CSVReaderSport(iS).getCourseNames());

        sportslistAdapter =
                new ArrayAdapter<>(
                        this,                           // Die aktuelle Umgebung (diese Activity)
                        R.layout.list_item_sportslist,  // ID der XML-Layout Datei
                        R.id.item_list_textview,        // ID des TextViews
                        sportsList);                    // Daten in einer ArrayList

        ListView sportslistListView = (ListView) this.findViewById(R.id.sportListView);
        sportslistListView.setAdapter(sportslistAdapter);
        sportslistAdapter.notifyDataSetChanged();
    }

    private void setActions() {
        sportsListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                //Hier wird aus dem Sportnamen ein Link erstellt //TODO wird hier sportsLink in uri eingefügt, wird _ als %20 angezeigt
                /*String sportsLink = sportsList.get(position);
                sportsLink.replace(" ", "_");
                sportsLink.replace("ä", "ae");
                sportsLink.replace("ö", "oe");
                sportsLink.replace("ü", "ue");
                sportsLink.replace("ß", "ss");
                sportsLink.replace("/", "_");
                sportsLink.replace("(", "_");
                sportsLink.replace(")", "_");*/

                Uri uri = Uri.parse("https://www.hochschulsport.ostfalia.de/angebote/aktueller_zeitraum/_" + sportsList.get(position).replace(" ", "_").replace("ä", "ae").replace("ö", "oe").replace("ü", "ue").replace("ß", "ss").replace("/", "_").replace("(", "_").replace(")", "_") + ".html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}