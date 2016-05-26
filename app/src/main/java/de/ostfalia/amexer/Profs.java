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
 * The class shows a listview with informations of professors.
 * It opens a telephonenumber of professors by clicking the name
 * @author Lina Tacke
 */
public class Profs extends AppCompatActivity {
    private ListView profsListView;
    private List<String> profsList;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gets the csv
        try {
            inputStream = this.getAssets().open(getString(R.string.profs_csv));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profs);

        fillList();
        profsListView = (ListView) findViewById(R.id.listView_profs);
        setActions();

        //Puts an Image to the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_profs);
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
        profsList = new ArrayList<>(new CSVReader(inputStream).getData());

        ArrayAdapter<String> profslistAdapter = new ArrayAdapter<>(
                this,                           // This Activity
                R.layout.list_item_profslist,  // ID from XML-Layout-Data
                R.id.item_list_textview,        // ID from TextViews
                profsList);

        ListView profslistListView = (ListView) this.findViewById(R.id.listView_profs);
        assert profslistListView != null;
        profslistListView.setAdapter(profslistAdapter);
        profslistAdapter.notifyDataSetChanged();
    }

   /**
     * Opens a telephone number by clicking on list-item(Prof)
     */
    private void setActions() {
        profsListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                String tel = profsList.get(position);
                tel = tel.split(" - ")[2];

                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                startActivity(callIntent);
            }
        });
    }
}
