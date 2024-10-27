import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        /**
         * Main method presents data as list, or search
         * Based on choice, prompts for a column to apply the choice to (columnChoices)
         * In case of search, a search term is also asked for
         * Carries out the request to the JobData via one of its methods and prints request
         */

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // An infinity loop/do-while loop- Allow the user to search until they manually quit
        while (true) {
            /**Uses the getUserSelection method/ utilizes the scanner class
             * actionChoice variable represents the user input from the actionChoices Hashmap
             * @method getUserSelection us asking us if we would like to search via list, or search
             */
            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {
                /**
                 * @method getUserSelection is asking what list, and if all is selected
                 * will use the findAll() method to display all jobs in JobData
                 */
                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {
                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                   // System.out.println("Search all fields not implemented yet.");
                    //TODO: add findByValue method here
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    /** ﻿Returns the key of the selected item from the choices Dictionary
     * @method getUserSelection()-A utility method that displays a menu of choices
     * and returns the user's selections
     * @param menuHeader
     * @param choices represents actionChoices or columnChoices, HashMaps
     * @return
     */

    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        /** associate an integer with each one, keySet() method is a Hashmap method
         * that returns key/values output
         */

        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }
        /** do-while loop continues as long as the condition is false
         * will continue until the user enters a valid input or enters x
         */
        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // TODO: TASK 1 Print a list of jobs

    /** @method prints a list of jobs to the console in a formatted manner
     *
     * @param someJobs represents a collection/arraylist of a hashmap (key/value pairs)
     */

    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        if(someJobs.isEmpty()){
            System.out.print("No Results");  // print instead of println for no extra space
        } else {
            //iterates over someJobs
            for(HashMap<String,String> job: someJobs){
                System.out.println("\n" +"*****");
                //iterates over key/values
                for(String key : job.keySet()){
                    //prints key and values
                    System.out.println(key + ": " + job.get(key));
                }
                System.out.println("*****");
            }
        }
    }
}
