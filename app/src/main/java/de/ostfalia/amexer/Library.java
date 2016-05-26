package de.ostfalia.amexer;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.ostfalia.amexer.entries.CSVReader;
import de.ostfalia.amexer.entries.TableHelper;
/**
 * This class uses a CSV-File 'semester_data.csv' to get the Semester-end-date and Semester-start-date.
 * After the Activity starts, the CVS-File will be loaded. The class gets the current
 * Date and Time, checks if Today is a workDay or Weekend.
 * If it is weekend The Activity shows a red "Geschlossen".
 * If it is a workday, it checks if TODAY is during Semester or Semester-free-time
 * The class gets from the activity_library_xml (from the Table) information about
 * the open-time-range for TODAY and compares it with the current time.
 * If current time is in Time-Range the library is "OFFEN" else "GRESCHLOSSEN"
 *
 * @author Natasza Szczypien
 */
public class Library extends AppCompatActivity {

    /* Activity-text that says if library is "OFFEN" or "GESCHLOSSEN" */
    private TextView libraryText;

    //  private InputStream iS;

    private Calendar semesterStart;
    private Calendar semesterEnde;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        InputStream iS = null;
        // Reads CSV
        try {
            iS = this.getAssets().open(getString(R.string.library_csv));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDates(iS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        context = this;

        setImageActionBar();
        initActivityObjects();
        setAvailibility();

    }

    /**
     * Initializing activity objects
     */
    private void initActivityObjects() {
        libraryText = (TextView) findViewById(R.id.library_text);
    }

    /**
     * Puts an Image into the Action Bar
     */
    private void setImageActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_library);
            actionBar.setDisplayShowTitleEnabled(false); // Removes text from the Action bar
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }


    /**
     * sets the Availibility text on the screen
     *
     * @return is needed because if it is a weekday he sets "Geschlossen" and doesn't go forward in the code
     */
    private boolean setAvailibility() {

        //Get current Time and date
        Calendar c = Calendar.getInstance();

        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        int dayType = c.get(Calendar.DAY_OF_WEEK);

        if (dayType == Calendar.SATURDAY || dayType == Calendar.SUNDAY) {

            libraryText.setText(R.string.closed);
            libraryText.setTextColor(Color.RED);
            return true;

        }

        TextView dayRow;
        String rowTime = "";

        try {
            if (isSemester(year, month)) {
                // Using the time from Semester Table
                switch (dayType) {
                    case Calendar.MONDAY:
                        dayRow = (TextView) findViewById(R.id.Mo_Se);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.TUESDAY:
                        dayRow = (TextView) findViewById(R.id.Tue_Se);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.WEDNESDAY:
                        dayRow = (TextView) findViewById(R.id.We_Se);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.THURSDAY:
                        dayRow = (TextView) findViewById(R.id.Thu_Se);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.FRIDAY:
                        dayRow = (TextView) findViewById(R.id.Fr_Se);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                }

            } else { // Semester-free-time
                switch (dayType) {

                    case Calendar.MONDAY:
                        dayRow = (TextView) findViewById(R.id.Mo_Vf);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.TUESDAY:
                        dayRow = (TextView) findViewById(R.id.Tue_Vf);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.WEDNESDAY:
                        dayRow = (TextView) findViewById(R.id.We_Vf);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.THURSDAY:
                        dayRow = (TextView) findViewById(R.id.Thu_Vf);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                    case Calendar.FRIDAY:
                        dayRow = (TextView) findViewById(R.id.Fr_Vf);
                        assert dayRow != null;
                        rowTime = dayRow.getText().toString();
                        break;
                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /* helper class for table */
        TableHelper tableHelper = new TableHelper();
        /* Prepares the current library open-time-range  */
        ArrayList<Integer> openHours = tableHelper.prepareTime(rowTime); // [openHour, openMinute, closeHour, closeMinute]
        int openHour = openHours.get(0);
        int openMinute = openHours.get(1);
        int closeHour = openHours.get(2);
        int closeMinute = openHours.get(3);

        if (tableHelper.isOpen(openHour, openMinute, closeHour, closeMinute, currentHour, currentMinute)) {
            libraryText.setText(R.string.open);
            libraryText.setTextColor(Color.GREEN);
        } else {
            libraryText.setText(R.string.closed);
            libraryText.setTextColor(Color.RED);
        }
        return true;
    }

    /**
     * Checks if the the current date is in semester time
     *
     * @param year current year
     * @param month current moth
     * @return semester time or not
     */
    private boolean isSemester(int year, int month) {
        return (semesterStart.get(Calendar.YEAR) == year) && (month >= semesterStart.get(Calendar.MONTH) && month <= semesterEnde.get(Calendar.MONTH)) && (month >= semesterStart.get(Calendar.DATE) && month <= semesterEnde.get(Calendar.DATE));
    }


    /* Sets all relevant dates */
    private void setDates(InputStream iS) {

        ArrayList<String> datesInString = new ArrayList<>(new CSVReader(iS).getData());
        ArrayList<Calendar> dates = new ArrayList<>();

        /* Fills the array with Date (Calendar) Objects */
        for (String line : datesInString) {
            String[] splitDate = line.split("-");
            int year = Integer.parseInt(splitDate[2]);
            int month = Integer.parseInt(splitDate[1]);
            int day = Integer.parseInt(splitDate[0]);

            Calendar date = new GregorianCalendar(year, month, day);
            dates.add(date);
        }

        semesterStart = dates.get(0);
        semesterEnde = dates.get(1);
    }
}
