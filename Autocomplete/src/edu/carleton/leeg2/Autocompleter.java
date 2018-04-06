/**
 * Autocompleter.java
 * Jeff Ondich, 20 March 2018
 * Ari Conati & Grant Lee, 3 April 2018
 *
 * This class exposes a very simple interface for generating auto-completions of search strings.
 * The purpose of this class is to give the students in CS257 an opportunity to practice creating
 * unit tests.
 */
package edu.carleton.leeg2;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Autocompleter {

    private List<Actor> actors; //List of actors loaded from file

    /**
     * @param dataFilePath the path to the data file containing the set of items to
     * from which auto-completed results will be drawn.
     */
    public Autocompleter(String dataFilePath) {
        actors = new ArrayList<Actor>();
        if (!load(dataFilePath, actors)) {
            System.err.println("File not found.");
            actors = null;
        }
    }

    /**
     *
     * @param dataFilePath - the file path of the file containing actors' names
     * @param actors - The list to which the actors in the file at dataFilePath
     * will be added. (Modified as a result of this method)
     * @return True if file was successfully loaded. False if file not found.
     */

    private boolean load(String dataFilePath, List<Actor> actors) {

        File file = new File(dataFilePath);
        Scanner scanner;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            Actor actor = new Actor(name);
            actors.add(actor);
        }
        return true;

    }

    /**
     * @param searchString the string whose autocompletions are sought
     * @return the list of potential completions that match the search string,
     * sorted in decreasing order of quality of the match (that is, the matches
     * are sorted from best match to weakest match)
     */
    public List<String> getCompletions(String searchString) {
        List<String> stringMatches = new ArrayList<String>();
        if(searchString == null ||searchString.equals("")) {
            return stringMatches; //return empty list if null/empty string
        }
        List<Actor> matches = new ArrayList<Actor>();

        //remove punctuation, spacing from search string; set to lowercase
        searchString = Actor.refineName(searchString);

        if (actors == null) {
            return stringMatches;
        }

        //If actor's name matches substring, add it to the list of matches
        //and set its priority level/index for comparison with other matches
        for (Actor actor : actors) {
            if (actor.getRefinedName().contains(searchString)) {
                actor.setHierarchy(searchString);
                matches.add(actor);
            }
        }

        //Sort list using compareTo() method of Actor class
        Collections.sort(matches);

        for (Actor actor : matches) { //convert actor objects to strings for return
            stringMatches.add(actor.toString());
        }

        return stringMatches;
    }

    /**
     * @param list - The list of strings to be printed.
     * Prints the contents with each item on a separate line.
     */
    public static void printResults(List<String> list) {
        for (String item : list) {
            System.out.println(item);
        }
    }

    /**
     * @param args - Command line arguments: The first argument is
     * the data path to the file containing actors' names
     * and the second is the search string.
     * Main method loads the contents of a file containing actors' names
     * and prints the sorted list of matches.
     */

    public static void main(String[] args) {
        if (!(args.length == 2)) {
            System.err.println("Incorrect command line usage. Correct usage: " +
                    "java Autocompleter pathToActorsFile searchString");
            System.exit(0);
        }
        Autocompleter autocompleter = new Autocompleter(args[0]);
        printResults(autocompleter.getCompletions(args[1]));
    }

    /**
     * The Actor nested static class that stores names in a neat fashion.
     */
    private static class Actor implements Comparable<Actor> {
        private String fullName; //Name of actor in Last, First format
        private String refinedName; // Name of actor with all spaces, punctuation, uppercase letters removed
        public int hierarchy; //Priority for actor - detailed in setHierarchy() method
        private int index; //Lowest index of the search string in
        private int comma; //Index of comma to distinguish between first and last name

        /**
         * Constructor for Actor Object
         * @param name the string that is imported into actor object
         */
        public Actor(String name) {
            this.fullName = name;
            this.refinedName = refineName(name);
            this.index = -1;
            this.hierarchy = -1;

            //If the name contains no comma, it is a single name
            //In this case, comma index set to the name's length
            if(name.contains(",")) {
                this.comma = refinedName.indexOf(",");
            } else {
                this.comma = refinedName.length();
            }
        }

        /**
         * Method used to get rid of punctuations and spaces in name
         * @param name the string that is being changed
         * @return a string that is cleaned to "lastname,firstname" format
         */
        public static String refineName(String name) {
            String refine = name.toLowerCase();
            refine = Normalizer.normalize(refine, Normalizer.Form.NFD);
            refine = refine.replaceAll("[^\\p{ASCII}]", "");
            refine = refine.replaceAll("\\p{M}", "");
            refine = refine.replaceAll("[\\\\\\s\\-\\'\\.]", "");
            return refine;
        }

        /**
         *
         * @param other - The actor to which this object is compared.
         * @return - A negative integer if this object is a better match,
         * zero if the actors are equally good matches, and a positive
         * number if this object is a worse match.
         */

        public int compareTo(Actor other) {

            //Compare hierarchy level, then searchstring's index in case of equal
            //hierarchy levels, then alphabetical comparison in case of equal index

            if (this.hierarchy != other.hierarchy) {
                return this.hierarchy - other.hierarchy;
            }
            if (this.index != other.index) {
                return this.index - other.index;
            }
            return this.refinedName.compareTo(other.refinedName);
        }

        /**
         * @Override
         * @return - The actor's name in the last, first format
         */

        public String toString() {
            return this.fullName;
        }

        /**
         *
         * @param searchString - The substring to be searched.
         * Sets the hierarchy instance variable of this object based on how closely
         * it matches the search string. 1: Matches beginning of last
         * name ; 2: Matches beginning of first name ; 3: Matches anywhere
         * in the last name ; 4: Matches anywhere in the first name ;
         * 5: Matches the middle of the string across the comma separating
         * first and last name.
         *
         * Additionally, sets the index instance variable of this object
         * to the index of the search string in the actor's name.
         */

        public void setHierarchy(String searchString) {
            //Set hierarchy, index to -1 if not a match
            if(!(refinedName.contains(searchString))) {
                this.hierarchy = -1;
                this.index = -1;
                return;
            }

            this.index = refinedName.indexOf(searchString);


            String first; //actor's first name

            //If the comma index is set to length, the actor's name
            //is a single last name, and the actor has no first name
            //Otherwise it is the index directly after the index of the comma
            if(!(comma == refinedName.length())){
                 first = refinedName.substring(comma + 1);
            } else {
                first = "";
            }

            if (searchString.contains(",")) { //matches across comma boundary
                this.hierarchy = 5;
            } else if (this.index == 0) { //beginning of last name
                this.hierarchy = 1;
            } else if (first.indexOf(searchString) == 0) { //beginning of first name
                this.hierarchy = 2;
            } else if (this.index < comma + 1) { //somewhere in last name
                this.hierarchy = 3;
            } else if (this.index > comma + 1) { //somewhere in first name
                this.hierarchy = 4;
            }


        }

        /**
         *
         * @return - actor's name in last_name, first_name format, with
         * all punctuation (-,',.) removed.
         */

        public String getRefinedName() {
            return this.refinedName;
        }
    }
}


