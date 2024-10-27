import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */


/** JobData class is responsible for importing data from the CSV file
 * Job Data is stored in the allJobs() ArrayList of HashMaps
 */

public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    /** is a constant that that creates a collection/list of HashMaps(Key/Value pairs
     *
     */
    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();
        //for-loop iterates through every "row"/HashMap in all jobs
        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);
            //if aValue from allJobs isn't in the values Arraylist, it will be added.
            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }
        //returns the arrayList of values
        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of the field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
        //iterates through each job(row) in allJobs-- each row is a HashMap
        for (HashMap<String, String> row : allJobs) {
            //column represents the key that is being searched, .get(column) accesses the value
            String aValue = row.get(column);
            //if the searched result contains the value its then added to the jobs ArrayList
            if (aValue.toUpperCase().contains(value.toUpperCase())) {
                //adds the job entry to the jobs arraylist aValue matches the values;p0-9-09IOPL:K./≤l./
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return      List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        // TODO TASK 2 & 3- implement this method
        ArrayList<HashMap<String,String>> jobs = new ArrayList<>();
        // iterates through the all allJobs arrayList
        for (int i = 0;  i< allJobs.size(); i++){
            //declares a hashmap job entry from the allJobs arrayList
            HashMap<String, String> job = allJobs.get(i);
            //iterates through the values of the job entry
            for(String aValue : job.values()){
                //checks if the aValue contains the value that is being search,
                //job will be added to the jobs arraylist if found
                if(aValue.toUpperCase().contains(value.toUpperCase())){
                    jobs.add(job);
                }
            }
        }
        return jobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            /*
            @Class FileReader used for reading character files
            @CSVParser parses files according to specified format
            getRecords retrieves records from CSV file as CSV record object
a             */
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
