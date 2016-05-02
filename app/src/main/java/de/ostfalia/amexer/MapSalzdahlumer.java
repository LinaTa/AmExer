package de.ostfalia.amexer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

public class MapSalzdahlumer extends AppCompatActivity {
    private ImageView imageViewMapSalzdahlumer;
    private InputStream inputStream;
    private List<String> salzdahlumerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gets the csv
        try {
            inputStream = this.getAssets().open("campus_salzdahlumer_data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_salzdahlumer);

        salzdahlumerList = new ArrayList<>(new CSVReader(inputStream).getData());
        imageViewMapSalzdahlumer = (ImageView) findViewById(R.id.imageViewMapSalzdahlumer);

        setActions();

        //Puts an Image to the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_maps);
            actionBar.setDisplayShowTitleEnabled(false); // deletes the text from action bar
            Log.i(this.getClass().toString(), " action bar");
        } else {
            Log.i(this.getClass().toString(), "no action bar");
        }
    }

    private void setActions() {
        imageViewMapSalzdahlumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });
    }

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
        String[] stringArray = salzdahlumerList.toArray(new String[0]);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
        modeList.setAdapter(modeAdapter);

        builder.setView(modeList);
        final Dialog dialog = builder.create();

        dialog.show();
    }
}