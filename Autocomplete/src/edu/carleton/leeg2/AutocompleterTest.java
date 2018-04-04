    /** Tests the AutoCompleter.java Class
 * Assignment 1 Phase 2
 * @author Ari Conati & Grant Lee
 */
package edu.carleton.leeg2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AutocompleterTest {

    private Autocompleter autocompleter;


    @BeforeEach
    void setUp() { autocompleter = new Autocompleter("actors.txt"); }

    @AfterEach
    void tearDown() {
        autocompleter = null;
    }

    //Edge Cases
    @Test
    void testEmptyStringInput() {
        List<String> initial  = autocompleter.getCompletions("");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {};
        assertArrayEquals(expected, actual, "Empty string input does not " +
                "return an empty list");
    }

    @Test
    void testNoMatchString() {
        List<String> initial  = autocompleter.getCompletions("Bacone");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {};
        assertArrayEquals(expected, actual, "String input with no matches " +
                "does not return an empty list");
    }

    @Test
    void testStringWithSpaces() {
        List<String> initial  = autocompleter.getCompletions("F a ri s");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Faris, Anna"};
        assertArrayEquals(expected, actual, "String input with spaces" +
                " does not return trimmed name.");
    }

    @Test
    void testNullStringInput() {
        List<String> initial = autocompleter.getCompletions(null);
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {};
        assertArrayEquals(expected, actual, "Null string input" +
                " does not return an empty list.");
    }

    @Test
    void testWeirdCharacterInput() {
	    autocompleter = new Autocompleter("actors.txt");
        List<String> initial = autocompleter.getCompletions("Beyonce");
        String[] actual = initial.toArray(new String[0]);
        String[] empty = {};
        assertNotEquals(empty, actual, "Names with non-ASCII" +
                " (accented) characters not found when ASCII version used.");
    }

    @Test
    void testNameApostropheInput() {
        List<String> initial = autocompleter.getCompletions("OBrien");
        String[] actual = initial.toArray(new String[0]);
        String[] empty = {};
        assertNotEquals(empty, actual, "Names with apostrophe" +
                " characters not found when apostrophe omitted in search");
    }

    @Test
    void testNameApostropheInput2() {
        List<String> initial = autocompleter.getCompletions("O'Brien");
        String[] actual = initial.toArray(new String[0]);
        String[] empty = {};
        assertNotEquals(empty, actual, "Names with apostrophe" +
                " characters not found when search string includes apostrophe.");
    }

    @Test
    void testNameHyphenInput() {
        List<String> initial = autocompleter.getCompletions("JeanPaul");
        String[] actual = initial.toArray(new String[0]);
        String[] empty = {};
        assertNotEquals(empty, actual, "Names with hyphen" +
                " characters not found when hyphen omitted in search");
    }

    @Test
    void testNameHyphenInput2() {
        List<String> initial = autocompleter.getCompletions("Jean-Paul");
        String[] actual = initial.toArray(new String[0]);
        String[] empty = {};
        assertNotEquals(empty, actual, "Names with hyphen" +
                " characters not found when search string includes hyphen");
    }

    @Test
    void testNamePeriodInput() {
        List<String> initial = autocompleter.getCompletions("FMurray");
        String[] actual = initial.toArray(new String[0]);
        String[] empty = {};
        assertNotEquals(empty, actual, "Names with period" +
                " characters not found when period omitted in search");
    }

    @Test
    void testNamePeriodInput2() {
        List<String> initial = autocompleter.getCompletions("F.Murray");
        String[] actual = initial.toArray(new String[0]);
        String[] empty = {};
        assertNotEquals(empty, actual, "Names with period" +
                " characters not found when search string includes period");
    }

    @Test
    void testSingleName() {
        List<String> initial = autocompleter.getCompletions("Cher");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Cher", "Hutcherson, Josh", "Kutcher, Ashton", "Fletcher, Laura"};
        assertArrayEquals(expected, actual, "Error when sorting" +
                " single names.");
    }

    @Test
    void testSingleName2() {
        autocompleter = new Autocompleter("edgeactors.txt");
        List<String> initial = autocompleter.getCompletions("her");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Cher", "Fletcher, Laura"};
        assertArrayEquals(expected, actual, "Single names" +
                " not treated as last names");
    }

    @Test
    void testLowercaseInput() {
        List<String> initial = autocompleter.getCompletions("faris");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Faris, Anna"};
        assertArrayEquals(expected, actual, "If input is lowercase" +
                " matches are not successfully found.");
    }

    @Test
    void testRandomCaps() {
        List<String> initial = autocompleter.getCompletions("fArIS");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Faris, Anna"};
        assertArrayEquals(expected, actual, "If input has nonsensical" +
                " casing matches are not successfully found.");
    }

    @Test
    void testMultipleMatch() {
	    autocompleter = new Autocompleter("edgeactors.txt");
        List<String> initial = autocompleter.getCompletions("Ei");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Simein, Eileen", "Mein, Hannah"};
        assertArrayEquals(expected, actual, "Incorrectly sorted" +
                " when substring matched beginning of first and middle of last.");
    }

    @Test
    void testMultipleMatch2() {
	    autocompleter = new Autocompleter("edgeactors.txt");
        List<String> initial = autocompleter.getCompletions("omar");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Omario, Mario", "James, Omar"};
        assertArrayEquals(expected, actual, "Incorrectly sorted" +
                " when substring matched beginning of last name and across " +
                "comma.");
    }

    @Test
    void testSameCategory() {
	    autocompleter = new Autocompleter("edgeactors.txt");
        List<String> initial = autocompleter.getCompletions("es");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Tress, Scott", "James, John", "James, Omar"};
        assertArrayEquals(expected, actual, "Incorrectly sorted" +
                " names in the same category based on index.");
    }

    @Test
    void commaSpaceTest() {
        List<String> initial = autocompleter.getCompletions("ston,j");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Aniston, Jennifer"};
        assertArrayEquals(expected, actual, "Spaces should not influence" +
                " the result.");
    }

    @Test
    void commaBigSpaceTest() {
        List<String> initial = autocompleter.getCompletions("ston , j ");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Aniston, Jennifer"};
        assertArrayEquals(expected, actual, "Spaces should not influence" +
                " the result.");
    }

    //Basic Cases
    @Test
    void SameExactLastTest() {
        List<String> initial = autocompleter.getCompletions("Gyllenhaal");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Gyllenhaal, Jake", "Gyllenhaal, Maggie"};
        assertArrayEquals(expected, actual, "When last names are exactly" +
		" equal should be based on index.");
    }

    @Test
    void SameExactFirstTest() {
        List<String> initial = autocompleter.getCompletions("Dakota");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Fanning, Dakota", "Johnson, Dakota"};
        assertArrayEquals(expected, actual, "When first names are exactly" +
		" equal and the indexes are equal should be alphabetical by last name.");
    }

    @Test
    void SameExactCommaTest() {
        List<String> initial = autocompleter.getCompletions("on, jennifer");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Hudson, Jennifer", "Aniston, Jennifer"};
        assertArrayEquals(expected, actual, "Comma should still follow " +
                "index rule.");
    }

    @Test
    void SameBeginningLastTest() {
        List<String> initial = autocompleter.getCompletions("Lang");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Lange, Jessica", "Langella, Frank"};
        assertArrayEquals(expected, actual, "When beginning of last names" +
		" are equal and of same index should follow the index rule.");
    }

    @Test
    void SameBeginningFirstTest() {
        List<String> initial = autocompleter.getCompletions("Gret");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Garbo, Greta", "Gerwig, Greta"};
        assertArrayEquals(expected, actual, "When beginning first names are" +
		" equal and of same index should be alphabetical.");
    }

    @Test
    void SameMatchesLastTest() {
        List<String> initial = autocompleter.getCompletions("fflec");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Affleck, Ben", "Affleck, Casey"};
        assertArrayEquals(expected, actual, "When portions of last names are" +
		" equal and of same index should be alphabetical.");
    }

    @Test
    void SameMatchesFirstTest() {
        List<String> initial = autocompleter.getCompletions("iett");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Lewis, Juliette", "Binoche, Juliette"};
        assertArrayEquals(expected, actual, "When portions of first names are" +
		" equal and of same index it should follow the index rule");
    }

    @Test
    void SameMatchesCommaIntersection() {
        List<String> initial = autocompleter.getCompletions("nes, J");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Jones, January", "Jones, Jennifer"};
        assertArrayEquals(expected, actual, "When comma intersections are equal," +
		" should follow original sorting rule.");
    }

    @Test
    void BeginningLast_MatchesFirst() {
        List<String> initial = autocompleter.getCompletions("greg");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Gregg, Clark", "Peck, Gregory", "Kinnear, Greg", "McGregor, Ewan"};
        assertArrayEquals(expected, actual, "Beginning Last should come before" +
		" Beginning First.");
    }

    @Test
    void BeginningLastName_MatchesLast() {
        List<String> initial = autocompleter.getCompletions("thew");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Thewlis, David", "Broderick, Matthew", "McConaughey, Matthew"};
        assertArrayEquals(expected, actual, "Beginning Last should precede" +
		" Matches First.");
    }

    @Test
    void BeginningLastName_MatchesFirst() {
        List<String> initial = autocompleter.getCompletions("asta");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Astaire, Fred", "Chastain, Jessica", "Kinski, Nastassja"};
        assertArrayEquals(expected, actual, "Beginning Last should precede" +
		" Matches First.");
    }

    @Test
    void BeginningFirst_MatchesLast() {
        List<String> initial = autocompleter.getCompletions("Dem");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = { "Moore, Demi", "Bardem, Javier"};
        assertArrayEquals(expected, actual, "Beginning First should precede" +
		" Matches Last.");
    }

    @Test
    void BeginningFirst_MatchesFirst() {
        List<String> initial = autocompleter.getCompletions("Ice");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Cube, Ice", "Eve, Alice", "Bejo, Bérénice" , "Straight, Beatrice"};
        assertArrayEquals(expected, actual, "Beginning First should precede" +
		" Matches First.");
    }

    @Test
    void MatchesLast_MatchesFirst() {
        List<String> initial = autocompleter.getCompletions("oom");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Bloom, Orlando", "Rapace, Noomi"};
        assertArrayEquals(expected, actual, "Matches Last should precede" +
		" Matches First.");
    }

    @Test
    void BasicsCumTest() {
        List<String> initial = autocompleter.getCompletions("ent");
        String[] actual = initial.toArray(new String[0]);
        String[] expected = {"Entertainer, Cedric the", "Osment, Haley Joel", "Potente, Franka", "Fiorentino, Linda",
	    "Laurent, Mélanie", "Broadbent, Jim", "Gallo, Vincent", "Cassel, Vincent"};
        assertArrayEquals(expected, actual, "Go pass all other basic tests and try again...");
    }
}
