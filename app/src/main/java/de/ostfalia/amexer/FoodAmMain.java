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
 * This class uses a CSV-File 'mensa_data.csv' to get the open and close time from the Mensa.
 * After the Activity starts, the CVS-File will be loaded. The class gets the current
 * Date and Time, checks if Today is a workDay or Weekend.
 * If it is weekend The Activity shows a red "Geschlossen".
 * The class compares the mensa open and close time with the current time.
 * If current time is in Time-Range the library is "OFFEN" else "GRESCHLOSSEN"
 *
 * @author Natasza Szczypien
 */
public class FoodAmMain extends AppCompatActivity {

    private int mensaOpenHour;
    private int mensaCloseHour;

    /* Activity Objects */
    private EditText mensaBigText;
    private EditText mensaSmallText;
    private ImageButton mensaButton;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_mensa);
        context = this;

        getOpenHoursFromCSV();
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
     * and sets the output on the activity_food_am_main
     */
    private void setAvailibility() {
        Calendar c = Calendar.getInstance();

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {

            mensaBigText.setText(R.string.wochenende);
            mensaBigText.setTextColor(Color.BLUE);
            mensaSmallText.setText(R.string.frei);

        } else {

            int currentHour = c.get(Calendar.HOUR_OF_DAY);

            if (currentHour >= mensaOpenHour && currentHour <= mensaCloseHour) {
                mensaBigText.setText(getString(R.string.open), TextView.BufferType.EDITABLE);
                mensaBigText.setTextColor(Color.GREEN);
                mensaSmallText.setText(getString(R.string.offen_bis) + mensaCloseHour + getString(R.string.zero_minute));
            } else {
                mensaBigText.setText(getString(R.string.closed), TextView.BufferType.EDITABLE);
                mensaBigText.setTextColor(Color.RED);
                mensaSmallText.setText(getString(R.string.wir_sehen_uns) + mensaOpenHour + getString(R.string.zero_minute));
            }
        }
    }

    /**
     * Initialize Activity Objects
     */
    private void initActivityObjects() {
        mensaBigText = (EditText) this.findViewById(R.id.mensa_text);
        mensaSmallText = (EditText) this.findViewById(R.id.mensa_time);
        mensaButton = (ImageButton) this.findViewById(R.id.mensa_button);
    }

    /**
     * When the Image will be clicked, than a Browser with the Restaurant menu will be openned.
     */
    private void setButtonAction() {
        mensaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.mensa_url));
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

        InputStream iS = null;
        // Reads CSV
        try {
            iS = this.getAssets().open(getString(R.string.mensa_csv));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Constants tells what the line index in the CSV means */
        int MENSA_OPEN_TIME_CSV = 0;
        int MENSA_CLOSE_TIME_CSV = 1;

        ArrayList<String> hoursInString = new ArrayList<>(new CSVReader(iS).getData());

        mensaOpenHour = Integer.parseInt(hoursInString.get(MENSA_OPEN_TIME_CSV));
        mensaCloseHour = Integer.parseInt(hoursInString.get(MENSA_CLOSE_TIME_CSV));
    }
}