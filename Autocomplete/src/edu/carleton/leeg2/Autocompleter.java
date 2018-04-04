/**
 * Autocompleter.java
 * Jeff Ondich, 20 March 2018
 * Ari Conati & Grant Lee
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

    private List<Actor> actors;
    /**
     * @param dataFilePath the path to the data file containing the set of items to
     * from which auto-completed results will be drawn. (In the context of this assignment,
     * this will be the path to the actors.txt file I provided you. But we'll also talk
     * later about how you might use inheritance to create subclasses of Autocompleter
     * to use different datasets and different approaches to doing the autocompletion.)
     */
    public Autocompleter(String dataFilePath) {
        actors = new ArrayList<Actor>();
        if(!load(dataFilePath, actors)) {
            System.err.println("File not found.");
            actors = null;
        }
    }

    private boolean load(String dataFilePath, List<Actor> actors) {

        File file = new File(dataFilePath);
        Scanner scanner;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return false;
        }

        while(scanner.hasNextLine()) {
            String name = scanner.nextLine();
            Actor actor = new Actor(name);
            actors.add(actor);
        }
        return true;

    }
    /**
     * @param searchString the string whose autocompletions are sought
     * @return the list of potential completions that match the search string,
     *  sorted in decreasing order of quality of the match (that is, the matches
     *  are sorted from best match to weakest match)
     */
    public List<String> getCompletions(String searchString) {
        List<String> matches = new ArrayList<String>();

        if(actors == null) {
            return matches;
        }

        for(Actor actor : actors) {
            if(actor.toString().contains(searchString)) {
                actor.setHierarchy(searchString);
                matches.add(actor.toString());
            }
        }

        Collections.sort(matches);

        return matches;
    }

    public static void printResults(List<String> list){
        for(String item : list) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) {
        if(!(args.length == 2)) {
            System.err.println("Incorrect command line usage. Correct usage:" +
                    "java Autocompleter pathToActorsFile searchString");
            System.exit(0);
        }
        Autocompleter autocompleter = new Autocompleter(args[0]);
        printResults(autocompleter.getCompletions(args[1]));
    }
}

    /**
     *
     */
    private static class Actor implements Comparable<Actor>{
        String fullName;
        String refinedName;
        int hierarchy = 6;
        int index;
        int comma;

        public Actor(String name) {
            this.fullName = name;

        }

        private String refineName(String name) {
            String refine = name.toLowerCase();
            refine = Normalizer.normalize(refine, Normalizer.Form.NFD);
            refine = refine.replaceAll("[^\\p{ASCII}]","");
            refine = refine.replaceAll("\\p{M}", "");
            refine.trim();
            refine = refine.replaceAll("\\s\\-\\'", "");
        }

        public int compareTo(Actor other) {
            if(this.hierarchy != other.hierarchy) {
                
            }

        }

    }
}

=======
}
>>>>>>> 0cdfa258d6c73216c8a8b83585b747f1060fb586
