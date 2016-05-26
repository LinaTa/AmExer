package de.ostfalia.amexer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import de.ostfalia.amexer.entries.CSVReader;

/**
 * This class uses a CSV-File 'solferino_limes_data.csv' to get the open and close time from the Mensa.
 * After the Activity starts, the CVS-File will be loaded. The class gets the current
 * Date and Time, checks if Today is a workDay or Weekend.
 * If it is weekend The Activity shows a red "Geschlossen".
 * The class compares the mensa open and close time with the current time.
 * If current time is in Time-Range the library is "OFFEN" else "GRESCHLOSSEN"
 *
 * @author Natasza Szczypien
 */
public class FoodAmExer extends AppCompatActivity {

    private int solferinoOpenHour;
    private int solferinoCloseHour;

    private int limesOpenHour;
    private int limesCloseHour;


    /* Activity Objects */
    private ImageButton solferinoButton;
    private TextView solferinoBigText;
    private TextView solferinoSmallText;
    private ImageButton limesButton;
    private TextView limesSmallText;
    private TextView limesBigText;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getOpenHoursFromCSV();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_exer);
        context = this;

        setImageActionBar();
        initActivityObjects();
        setButtonAction();
        setAvailibility();

    }

    /**
     * Puts an Image to the Action Bar
     */
    private void setImageActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_food);
            actionBar.setDisplayShowTitleEnabled(false); // Removes text from the Action bar
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }

    /**
     * Gets the current date and time, compares with the opening times of the restaurants
     * and sets the output on the activity_food_am_exer
     */
    private void setAvailibility() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);


        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {

            solferinoBigText.setText(R.string.wochenende);
            solferinoBigText.setTextColor(Color.BLUE);
            solferinoSmallText.setText(R.string.frei);
            limesBigText.setText(R.string.wochenende);
            limesBigText.setTextColor(Color.BLUE);
            limesSmallText.setText(R.string.frei);

        } else {

            int currentHour = c.get(Calendar.HOUR_OF_DAY);

            if (currentHour >= solferinoOpenHour && currentHour < solferinoCloseHour) {
                solferinoBigText.setText(R.string.open, TextView.BufferType.EDITABLE);
                solferinoBigText.setTextColor(Color.GREEN);
                solferinoSmallText.setText(getString(R.string.offen_bis) + " " + solferinoCloseHour + getString(R.string.zero_minute));
            } else {
                solferinoBigText.setText(R.string.closed, TextView.BufferType.EDITABLE);
                solferinoBigText.setTextColor(Color.RED);
                solferinoSmallText.setText(getString(R.string.wir_sehen_uns) + " " + solferinoOpenHour + getString(R.string.zero_minute));
            }

            if (currentHour >= limesOpenHour && currentHour < limesCloseHour) {
                limesBigText.setText(R.string.open, TextView.BufferType.EDITABLE);
                limesBigText.setTextColor(Color.GREEN);
                limesSmallText.setText(getString(R.string.offen_bis) + " " + limesCloseHour + getString(R.string.zero_minute));
            } else {
                limesBigText.setText(R.string.closed, TextView.BufferType.EDITABLE);
                limesBigText.setTextColor(Color.RED);
                limesSmallText.setText(getString(R.string.wir_sehen_uns) + " " + limesOpenHour + getString(R.string.zero_minute));
            }
        }
    }

    /**
     * Initialize Activity Objects
     */
    private void initActivityObjects() {
        solferinoBigText = (TextView) findViewById(R.id.solferino_state);
        solferinoSmallText = (TextView) findViewById(R.id.solferino_text);
        solferinoButton = (ImageButton) findViewById(R.id.solferino_image);

        limesBigText = (TextView) findViewById(R.id.limes_state);
        limesSmallText = (TextView) findViewById(R.id.limes_text);
        limesButton = (ImageButton) findViewById(R.id.limes_image);
    }

    /**
     * When the Image will be clicked, than a Browser with the Restaurant menu will be openned.
     */
    private void setButtonAction() {

        solferinoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.solferino_url));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        limesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.limes_url));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    /**
     * Gets the openning and closing times from a CSV File and assings it to
     * private variables.
     *
     */
    private void getOpenHoursFromCSV() {

        /*Constants tells what the line index in the CSV means*/
        int SOLFERINO_OPEN_TIME_CSV = 0;
        int SOLFERINO_CLOSE_TIME_CSV = 1;
        int LIMES_OPEN_TIME_CSV = 2;
        int LIMES_CLOSE_TIME_CSV = 3;

        InputStream iS = null;
        // Reads CSV
        try {
            iS = this.getAssets().open(getString(R.string.solferino_limes_csv));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> hoursInString = new ArrayList<>(new CSVReader(iS).getData());

        solferinoOpenHour = Integer.parseInt(hoursInString.get(SOLFERINO_OPEN_TIME_CSV));
        solferinoCloseHour = Integer.parseInt(hoursInString.get(SOLFERINO_CLOSE_TIME_CSV));

        limesOpenHour = Integer.parseInt(hoursInString.get(LIMES_OPEN_TIME_CSV));
        limesCloseHour = Integer.parseInt(hoursInString.get(LIMES_CLOSE_TIME_CSV));
    }
}