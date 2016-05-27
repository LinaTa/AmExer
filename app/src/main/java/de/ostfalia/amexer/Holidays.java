package de.ostfalia.amexer;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.ostfalia.amexer.entries.CSVReader;

/**
 * Activity for holidays-table
 * Shows the holidays in a table
 * Shows if today is a holiday or not
 * The class gets the sportcourses form the sports.data.csv
 * @author Lina Tacke
 */
public class Holidays extends AppCompatActivity {
    private List<String> holidaysList;
    private InputStream inputStream;
    private TextView textViewHoliday;
    private TableLayout table;

    /**
     * Initialize the activity
     * @param savedInstanceState the user's current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gets the CSV
        try {
            inputStream = this.getAssets().open(getString(R.string.holidays_data_csv));
        } catch (IOException e) {
            e.printStackTrace();
        }

        holidaysList = new ArrayList<>(new CSVReader(inputStream).getData());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays_table);

        textViewHoliday = (TextView) findViewById(R.id.textView_holiday_free);
        table = (TableLayout) findViewById(R.id.table_holidays);

        fillTable();

        setActionBar();
    }

    //Puts an image into the actionbar and removes the appname
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //Puts an image into the actionbar
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_holidays);
            //Removes the text in action bar
            actionBar.setDisplayShowTitleEnabled(false);
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }

    /**
     * Fills the table with data
     */
    private void fillTable() {
        for (String str : holidaysList){
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.5f);

            TableRow rowHoliday = new TableRow(this);
            rowHoliday.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            rowHoliday.setGravity(Gravity.CENTER_VERTICAL);
            rowHoliday.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView name = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                name.setBackground(getDrawable(R.drawable.cell_shape));
            }
            name.setGravity(Gravity.CENTER_HORIZONTAL);
            name.setLayoutParams(lp);

            TextView date = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                date.setBackground(getDrawable(R.drawable.cell_shape));
            }
            date.setGravity(Gravity.CENTER_HORIZONTAL);
            date.setLayoutParams(lp);

            String[] split = str.split(",");
            name.setText(split[0]);
            date.setText(split[1]);

            rowHoliday.addView(name);
            rowHoliday.addView(date);
            table.addView(rowHoliday);
        }

        checkHolidays();
    }

    /**
     * Checks if it's a holiday or not
     */
    private void checkHolidays() {
        Calendar today = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

        for (String holiday : holidaysList){
            Calendar startDate;
            Calendar endDate = null;

            //Gets date in format "DD.MM.YYYY" or "DD.MM.YYYY-DD.MM.YYYY"
            String holidayDate = holiday.split(",")[1];

            if(holidayDate.contains("-")){
                //Holiday longer than 1 day
                String startTime = holidayDate.split("-")[0];
                String endTime = holidayDate.split("-")[1];

                int startDay =  Integer.parseInt(startTime.split("\\.")[0]);
                int startMonth = Integer.parseInt(startTime.split("\\.")[1]);
                int startYear = Integer.parseInt(startTime.split("\\.")[2]);
                int endDay = Integer.parseInt(endTime.split("\\.")[0]);
                int endMonth = Integer.parseInt(endTime.split("\\.")[1]);
                int endYear = Integer.parseInt(endTime.split("\\.")[2]);

                startDate = new GregorianCalendar(startYear, startMonth-1, startDay);        // month begins with 0
                endDate = new GregorianCalendar(endYear, endMonth-1, endDay);                // month begins with 0
            }else{
                //Holiday with 1 day
                int startDay = Integer.parseInt(holidayDate.split("\\.")[0]);
                int startMonth = Integer.parseInt(holidayDate.split("\\.")[1]);
                int startYear = Integer.parseInt(holidayDate.split("\\.")[2]);

                startDate = new GregorianCalendar(startYear, startMonth-1, startDay);        // month begins with 0
            }

            if (endDate == null){
                //One day
                if(today.equals(startDate)){
                    textViewHoliday.setText(R.string.semesterBreak);
                    textViewHoliday.setTextColor(ContextCompat.getColor(this, R.color.ostfaliaGreen));
                }
            }else{
                // More than one day (checks if today is startDate, endDate or between these dates)
                if(today.after(startDate) && today.before(endDate) || today.equals(startDate) || today.equals(endDate)){
                    textViewHoliday.setText(R.string.semesterBreak);
                    textViewHoliday.setTextColor(ContextCompat.getColor(this, R.color.ostfaliaGreen));
                }
            }
        }
    }
}
