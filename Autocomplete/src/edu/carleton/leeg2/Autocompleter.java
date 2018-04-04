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

    public List<Actor> actors;

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
     *               will be added. (Modified as a result of this method)
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
        List<String> matches = new ArrayList<String>();
        searchString = Actor.refineName(searchString);

        if (actors == null) {
            return matches;
        }

        for (Actor actor : actors) {
            if (actor.refinedName.contains(searchString)) {
                actor.setHierarchy(searchString);
                matches.add(actor.toString());
            }
        }

        Collections.sort(matches);

        return matches;
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
            System.err.println("Incorrect command line usage. Correct usage:" +
                    "java Autocompleter pathToActorsFile searchString");
            System.exit(0);
        }
        Autocompleter autocompleter = new Autocompleter(args[0]);
        printResults(autocompleter.getCompletions(args[1]));
    }

    /**
     * The Actor nested static class that stores names in a neat fashion.
     */
    public static class Actor implements Comparable<Actor> {
        String fullName;
        String refinedName;
        int hierarchy;
        int index;
        int comma;

        /**
         * Constructor for Actor Object
         * @param name the string that is imported into actor object
         */
        public Actor(String name) {
            this.fullName = name;
            this.refinedName = refineName(name);
            this.index = -1;
            this.hierarchy = -1;
            this.comma = refinedName.indexOf(",");
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
            refine.trim();
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
            if (this.hierarchy != other.hierarchy) {
                return this.hierarchy - other.hierarchy;
            }
            if (this.index != other.index) {
                return this.index - other.index;
            }
            return this.fullName.compareTo(other.fullName);

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


        }
        public String getRefinedName() {
            return this.refinedName;
        }
    }
}


