/** Tests the AutoCompleter.java Class
 * Assignment 1 Phase 2
 * @author Ari Conati & Grant Lee
 */
package edu.carleton.leeg2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AutocompleterTest {

    private Autocompleter autocompleter;

    @BeforeEach
    void setUp() {
        autocompleter = new Autocompleter("edgeactor.txt");
    }

    @AfterEach
    void tearDown() {
        autocompleter = null;
    }

    //Edge Cases


    //Basic Cases
    @Test
    void SameExactLastTest() {
        List<String> actual = autocompleter.getCompletions("Gyllenhaal");
        String[] expected = {"Gyllenhaal, Jake", "Gyllenhaal, Maggie"};
        assertEquals(expected, actual.toArray(), "When last names are exactly equal should be alphabetical by first name");
    }

    @Test
    void SameExactFirstTest() {
        List<String> actual = autocompleter.getCompletions("Dakota");
        String[] expected = {"Fanning, Dakota", "Johnson, Dakota"};
        assertEquals(expected, actual.toArray(), "When first names are exactly equal should be alphabetical by last name");
    }

    @Test
    void SameBeginningLastTest() {
        List<String> actual = autocompleter.getCompletions("Lang");
        String[] expected = {"Lange, Jessica", "Langella, Frank"};
        assertEquals(expected, actual.toArray(), "When beginning of last names are equal and of same index should be alphabetical");
    }

    @Test
    void SameBeginningFirstTest() {
        List<String> actual = autocompleter.getCompletions("Gret");
        String[] expected = {"Garbo, Greta", "Gerwig, Greta"};
        assertEquals(expected, actual.toArray(), "When beginning first names are equal and of same index should be alphabetical");
    }

    @Test
    void SameMatchesLastTest() {
        List<String> actual = autocompleter.getCompletions("fflec");
        String[] expected = {"Affleck, Ben", "Affleck, Casey"};
        assertEquals(expected, actual.toArray(), "When portions of last names are equal and of same index should be alphabetical");
    }

    @Test
    void SameMatchesFirstTest() {
        List<String> actual = autocompleter.getCompletions("iett");
        String[] expected = {"Binoche, Juliette", "Lewis, Juliette"};
        assertEquals(expected, actual.toArray(), "When portions of first names are equal and of same index should be alphabetical");
    }

    @Test
    void SameMatchesCommaIntersection() {
        List<String> actual = autocompleter.getCompletions("enw");
        String[] expected = {"Allen, Woody", "Holden, William"};
        assertEquals(expected, actual.toArray(), "When comma intersections are equal, should follow original sorting rule");
    }

    @Test
    void BeginningLast_MatchesFirst() {
        List<String> actual = autocompleter.getCompletions("greg");
        String[] expected = {"Gregg, Clark", "Kinnear, Greg", "Peck, Gregory", "McGregor, Ewan"};
        assertEquals(expected, actual.toArray(), "Beginning Last should come before Beginning First");
    }

    @Test
    void BeginningLastName_MatchesLast() {
        List<String> actual = autocompleter.getCompletions("thew");
        String[] expected = {"Thewlis, David", "Broderick, Matthew", "McConaughey, Matthew"};
        assertEquals(expected, actual.toArray(), "Beginning Last should precede Matches First");
    }

    @Test
    void BeginningLastName_MatchesFirst() {
        List<String> actual = autocompleter.getCompletions("asta");
        String[] expected = {"Astaire, Fred", "Chastain, Jessica", "Kinski, Nastassja"};
                assertEquals(expected, actual.toArray(), "Beginning Last should precede Matches First");
    }

    @Test
    void BeginningLastName_MatchesComma() {
        List<String> actual = autocompleter.getCompletions("lom");
        String[] expected = {"Lombard, Carole", "Bello, Maria", "Ruffalo, Mark"};
        assertEquals(expected, actual.toArray(), "Beginning Last should precede Comma Match");
    }

    @Test
    void BeginningFirst_MatchesLast() {
        List<String> actual = autocompleter.getCompletions("Dem");
        String[] expected = { "Moore, Demi", "Bardem, Javier"};
        assertEquals(expected, actual.toArray(), "Beginning First should precede Matches Last");
    }

    @Test
    void BeginningFirst_MatchesFirst() {
        List<String> actual = autocompleter.getCompletions("Ice");
        String[] expected = {"Cube, Ice", "Eve, Alice", "Straight, Beatrice", "Bejo, Bérénice"};
        assertEquals(expected, actual.toArray(), "Beginning First should precede Matches First");
    }

    @Test
    void BeginningFirst_MatchesComma() {
        List<String> actual = autocompleter.getCompletions("rash");
        String[] expected = {"Jones, Rashida", "Kutcher, Ashton"};
        assertEquals(expected, actual.toArray(), "Beginning First should precede Matches Comma");
    }

    @Test
    void MatchesLast_MatchesFirst() {
        List<String> actual = autocompleter.getCompletions("oom");
        String[] expected = {"Bloom, Orlando", "Rapace, Noomi"};
        assertEquals(expected, actual.toArray(), "Matches Last should precede Matches First");
    }

    @Test
    void MatchesLast_MatchesComma() {
        List<String> actual = autocompleter.getCompletions("aca");
        String[] expected = {"Bacall, Lauren", "Culkin, Macaulay", "Miranda, Carmen"};
        assertEquals(expected, actual.toArray(), "Matches Last should precede Matches Comma");
    }

    @Test
    void BasicsCumTest() {
        List<String> actual = autocompleter.getCompletions("ent");
        String[] expected = {"Entertainer, Cedric the", "Osment, Haley Joel", "Potente, Franka", "Fiorentino, Linda", "Laurent, Mélanie", "Broadbent, Jim", "Cassel, Vincent", "Gallo, Vincent", "Allen, Tim"};
        assertEquals(expected, actual.toArray(), "Go pass all other basic tests and try again...");
    }
}
