package de.ostfalia.amexer.entries;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Lina on 14.04.2016.
 */
public class CSVReaderSport {
    private ArrayList<String> courseNames = new ArrayList<>();

    public CSVReaderSport(InputStream iS) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(iS));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                courseNames.add(split[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getCourseNames(){
        return courseNames;
    }
}
