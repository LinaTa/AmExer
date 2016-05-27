package de.ostfalia.amexer.entries;

import java.util.ArrayList;
/**
 * The class is a helper class for Data center and library activity.
 * It parses the string times from the tables into a int array with the times.
 * It checks also if current time is in opening time range from the library
 *  or data center .
 *
 * @author Natasza Szczypien
 * Created by livruen on 22.05.16.
 */

public class TableHelper {
    /**
     * Parses the
     * @param rowTime1, rowTime2  in the Format "  00:00-00:00  "
     * @return  to an array with strings [00,00,00,00]
     */
    public ArrayList<Integer> prepareTime(String rowTime1 /*,String rowTime2*/) {

        /* parses the rowTime*/
        rowTime1 = rowTime1.trim();
        ArrayList<Integer> tempList = new ArrayList<>(); // [00:00-00:00]
        String[] twoTemps = rowTime1.split("–");
        String[] timeOne = twoTemps[0].split(":");
        String[] timeTwo = twoTemps[1].split(":");

        tempList.add(Integer.parseInt(timeOne[0].trim()));
        tempList.add(Integer.parseInt(timeOne[1].trim()));
        tempList.add(Integer.parseInt(timeTwo[0].trim()));
        tempList.add(Integer.parseInt(timeTwo[1].trim()));

        return tempList;
    }

    /**
     *Checks if Current time is in the open -time-range
     * @param openHour today open hour from choosed restaurant
     * @param openMinute today open minute from choosed restaurant
     * @param closeHour today close hour from choosed restaurant
     * @param closeMinute [Could be used in the future when minute != 00 ] today close minute from choosed restaurant
     * @param currentHour current hour
     * @param currentMinute current minute
     * @return open or closed
     */
    public boolean isOpen(int openHour, int openMinute, int closeHour, int closeMinute, int currentHour, int currentMinute) {

        if (currentHour == openHour) {
            return currentMinute >= openMinute;

        } else  {
            return currentHour > openHour && currentHour <= closeHour;
        }
    }
}
