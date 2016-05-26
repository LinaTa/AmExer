package de.ostfalia.amexer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * The class shows a listview with informations of professors.
 * It opens a telephonenumber of professors by clicking the name
 * @author Lina Tacke
 */
public class Profs extends AppCompatActivity {
    private Button button_electrical_engineering;
    private Button button_informatik;
    private Button button_machine_engineering;
    private Button button_law;
    private Button button_social_work;
    private Button button_supply_engineering;

    /**
     * Initialize the activity
     * @param savedInstanceState the user's current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profs);

        button_electrical_engineering = (Button) findViewById(R.id.button_electrical_engineering);
        button_informatik = (Button) findViewById(R.id.button_informatik);
        button_machine_engineering = (Button) findViewById(R.id.button_machine_engineering);
        button_law = (Button) findViewById(R.id.button_law);
        button_social_work = (Button) findViewById(R.id.button_social_work);
        button_supply_engineering = (Button) findViewById(R.id.button_supply_engineering);

        setActions();
        setActionBar();
    }

    /**
     * Opens a activity (list of profs) from the choosen faculty
     */
    private void setActions() {
        button_electrical_engineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profs.this, ProfsElectricalEngineering.class));
            }
        });

        button_informatik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profs.this, ProfsInformatik.class));
            }
        });

        button_machine_engineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profs.this, ProfsMachineEngineering.class));
            }
        });

        button_law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profs.this, ProfsLaw.class));
            }
        });

        button_social_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profs.this, ProfsSocialWork.class));
            }
        });

        button_supply_engineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profs.this, ProfsSupplyEngineering.class));
            }
        });
    }

    /**
     * Puts an image into the actionbar and removes the appname
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // puts an image into the actionbar
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_profs);
            // removes the Text from action-bar
            actionBar.setDisplayShowTitleEnabled(false);
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }
}
