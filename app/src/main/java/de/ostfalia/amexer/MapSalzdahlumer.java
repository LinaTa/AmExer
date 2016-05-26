package de.ostfalia.amexer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.ostfalia.amexer.entries.CSVReader;

/**
 * Activity for showing a map of the salzdahlumer-campus
 * By clicking the map it shows a popup with informations of the map
 * @author Lina Tacke
 */
public class MapSalzdahlumer extends AppCompatActivity {
    private ImageView imageViewMapSalzdahlumer;
    private InputStream inputStream;
    private List<String> salzdahlumerList;

    /**
     * Initialize the activity
     * @param savedInstanceState the user's current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gets the csv
        try {
            inputStream = this.getAssets().open(getString(R.string.campus_salzdahlumer_data_csv));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_salzdahlumer);

        salzdahlumerList = new ArrayList<>(new CSVReader(inputStream).getData());
        imageViewMapSalzdahlumer = (ImageView) findViewById(R.id.imageViewMapSalzdahlumer);

        setActions();
        setImage();
        setActionBar();
    }

    /**
     * Puts an image into the actionbar and removes the appname
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //Puts an image into the actionbar
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_maps);
            //Removes the text in action bar
            actionBar.setDisplayShowTitleEnabled(false);
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }

    /**
     * Sets the Map-Image in landscape or portrait-orientation
     */
    private void setImage() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageViewMapSalzdahlumer.setImageResource(R.drawable.map_salzdahlumer_quer);
        } else {
            imageViewMapSalzdahlumer.setImageResource(R.drawable.map_salzdahlumer);
        }
    }

    /**
     * Sets the Action on the Map (it shows a PopUp with informations of the map)
     */
    private void setActions() {
        imageViewMapSalzdahlumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });
    }

    /**
     * pens a popup with a list with informations Sets the Action on the Map
     */
    private void showList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_maps);
        builder.setTitle(R.string.gebaudeSalzdahlumer);

        builder.setNegativeButton(
                R.string.close,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        ListView modeList = new ListView(this);
        String[] stringArray = salzdahlumerList.toArray(new String[salzdahlumerList.size()]);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
        modeList.setAdapter(modeAdapter);

        builder.setView(modeList);
        final Dialog dialog = builder.create();

        dialog.show();
    }
}
