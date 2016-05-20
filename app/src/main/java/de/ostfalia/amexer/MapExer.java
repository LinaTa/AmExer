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
 * Activity for showing a map of the exercampus
 */
public class MapExer extends AppCompatActivity {
    private ImageView imageViewMapExer;
    private InputStream inputStream;
    private List<String> exerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gets the csv
        try {
            inputStream = this.getAssets().open("campus_exer_data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_exer);

        exerList = new ArrayList<>(new CSVReader(inputStream).getData());
        imageViewMapExer = (ImageView) findViewById(R.id.imageViewMapExer);

        setActions();
        setImage();

        //Puts an Image to the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_maps);
            // deletes the text from action bar
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
            imageViewMapExer.setImageResource(R.drawable.map_exer_quer);
        } else {
            imageViewMapExer.setImageResource(R.drawable.map_exer);
        }
    }

    /**
     * Sets the action by clicking image (popup with info)
     */
    private void setActions() {
        imageViewMapExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });
    }

    /**
     * Opens a popup with a list with informations
     */
    private void showList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_maps);
        builder.setTitle(R.string.gebaudeExer);

        builder.setNegativeButton(
                R.string.close,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        ListView modeList = new ListView(this);
        String[] stringArray = exerList.toArray(new String[exerList.size()]);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
        modeList.setAdapter(modeAdapter);

        builder.setView(modeList);
        final Dialog dialog = builder.create();

        dialog.show();
    }
}
