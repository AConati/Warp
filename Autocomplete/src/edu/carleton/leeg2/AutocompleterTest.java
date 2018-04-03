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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AutocompleterTest {

    private Autocompleter autocompleter;

    @AfterEach
    void tearDown() {
        autocompleter = null;
    }

    //Edge Cases
    @Test
    void testEmptyStringInput() {
	autocompleter = new Autocompleter("edgeactors.txt");
        List<String> actual  = autocompleter.getCompletions("");
        String[] expected = {};
        assertEquals(expected, actual.toArray(), "Empty string input does not " +
                "return an empty list");
    }

    @Test
    void testNoMatchString() {
	autocompleter = new Autocompleter("edgeactors.txt");
        List<String> actual  = autocompleter.getCompletions("Bacone");
        String[] expected = {};
        assertEquals(expected, actual.toArray(), "String input with no matches " +
                "does not return an empty list");
    }

    @Test
    void testStringWithSpaces() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual  = autocompleter.getCompletions("F a ri s");
        String[] expected = {"Faris, Anna"};
        assertEquals(expected, actual.toArray(), "String input with spaces" +
                "does not return trimmed name.");

    }

    @Test
    void testNullStringInput() {
	autocompleter = new Autocompleter("edgeactors.txt");
        List<String> actual = autocompleter.getCompletions(null);
        String[] expected = {};
        assertEquals(expected, actual.toArray(), "Null string input" +
                "does not return an empty list.");

    }

    @Test
    void testWeirdCharacterInput() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Beyonce");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with non-ASCII" +
                "(accented) characters not found when ASCII version used.");

    }

    @Test
    void testNameApostropheInput() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("OBrien");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with apostrophe" +
                "characters not found when apostrophe omitted in search");
    }

    @Test
    void testNameApostropheInput2() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("O'Brien");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with apostrophe" +
                "characters not found when search string includes apostrophe.");
    }

    @Test
    void testNameHyphenInput() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("JeanPaul");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with hyphen" +
                "characters not found when hyphen omitted in search");
    }

    @Test
    void testNameHyphenInput2() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Jean-Paul");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with hyphen" +
                "characters not found when search string includes hyphen");
    }

    @Test
    void testNamePeriodInput() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("FMurray");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with period" +
                "characters not found when period omitted in search");
    }

    @Test
    void testNamePeriodInput2() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("F.Murray");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with period" +
                "characters not found when search string includes period");
    }

    @Test
    void testSingleName() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Cher");
        String[] expected = {"Cher", "Fletcher, Laura"};
        assertEquals(expected, actual.toArray(), "Error when sorting" +
                "single names.");

    }

    @Test
    void testSingleName2() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("her");
        String[] expected = {"Cher", "Fletcher, Laura"};
        assertEquals(expected, actual.toArray(), "Single names" +
                "not treated as last names");

    }

    @Test
    void testLowercaseInput() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("faris");
        String[] expected = {"Faris, Anna"};
        assertEquals(expected, actual.toArray(), "If input is lowercase" +
                "matches are not successfully found.");
    }

    @Test
    void testRandomCaps() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("fArIS");
        String[] expected = {"Faris, Anna"};
        assertEquals(expected, actual.toArray(), "If input has nonsensical" +
                "casing matches are not successfully found.");

    }

    @Test
    void testMultipleMatch() {
	autocompleter = new Autocompleter("edgeactors.txt");
        List<String> actual = autocompleter.getCompletions("Ei");
        String[] expected = {"Simein, Eileen", "Mein, Hannah"};
        assertEquals(expected, actual.toArray(), "Incorrectly sorted" +
                "when substring matched beginning of first and middle of last.");

    }

    @Test
    void testMultipleMatch2() {
	autocompleter = new Autocompleter("edgeactors.txt");
        List<String> actual = autocompleter.getCompletions("omar");
        String[] expected = {"Omario, Mario", "James, Omar"};
        assertEquals(expected, actual.toArray(), "Incorrectly sorted" +
                "when substring matched beginning of last name and across " +
                "comma.");
    }

    @Test
    void testSameCategory() {
	autocompleter = new Autocompleter("edgeactors.txt");
        List<String> actual = autocompleter.getCompletions("es");
        String[] expected = {"Tress, Scott", "James, John"};
        assertEquals(expected, actual.toArray(), "Incorrectly sorted" +
                "names in the same category based on index.");
    }


    //Basic Cases
    @Test
    void SameExactLastTest() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Gyllenhaal");
        String[] expected = {"Gyllenhaal, Jake", "Gyllenhaal, Maggie"};
        assertEquals(expected, actual.toArray(), "When last names are exactly" + 
		" equal should be alphabetical by first name");
    }

    @Test
    void SameExactFirstTest() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Dakota");
        String[] expected = {"Fanning, Dakota", "Johnson, Dakota"};
        assertEquals(expected, actual.toArray(), "When first names are exactly" + 
		" equal should be alphabetical by last name");
    }

    @Test
    void SameBeginningLastTest() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Lang");
        String[] expected = {"Lange, Jessica", "Langella, Frank"};
        assertEquals(expected, actual.toArray(), "When beginning of last names" +
		" are equal and of same index should be alphabetical");
    }

    @Test
    void SameBeginningFirstTest() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Gret");
        String[] expected = {"Garbo, Greta", "Gerwig, Greta"};
        assertEquals(expected, actual.toArray(), "When beginning first names are" +
		" equal and of same index should be alphabetical");
    }

    @Test
    void SameMatchesLastTest() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("fflec");
        String[] expected = {"Affleck, Ben", "Affleck, Casey"};
        assertEquals(expected, actual.toArray(), "When portions of last names are" + 
		" equal and of same index should be alphabetical");
    }

    @Test
    void SameMatchesFirstTest() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("iett");
        String[] expected = {"Binoche, Juliette", "Lewis, Juliette"};
        assertEquals(expected, actual.toArray(), "When portions of first names are" + 
		" equal and of same index should be alphabetical");
    }

    @Test
    void SameMatchesCommaIntersection() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("enw");
        String[] expected = {"Allen, Woody", "Holden, William"};
        assertEquals(expected, actual.toArray(), "When comma intersections are equal," + 
		" should follow original sorting rule");
    }

    @Test
    void BeginningLast_MatchesFirst() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("greg");
        String[] expected = {"Gregg, Clark", "Kinnear, Greg", "Peck, Gregory", "McGregor, Ewan"};
        assertEquals(expected, actual.toArray(), "Beginning Last should come before" + 
		" Beginning First");
    }

    @Test
    void BeginningLastName_MatchesLast() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("thew");
        String[] expected = {"Thewlis, David", "Broderick, Matthew", "McConaughey, Matthew"};
        assertEquals(expected, actual.toArray(), "Beginning Last should precede" +
		" Matches First");
    }

    @Test
    void BeginningLastName_MatchesFirst() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("asta");
        String[] expected = {"Astaire, Fred", "Chastain, Jessica", "Kinski, Nastassja"};
        assertEquals(expected, actual.toArray(), "Beginning Last should precede" +
		" Matches First");
    }

    @Test
    void BeginningLastName_MatchesComma() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("lom");
        String[] expected = {"Lombard, Carole", "Bello, Maria", "Ruffalo, Mark"};
        assertEquals(expected, actual.toArray(), "Beginning Last should precede" + 
		" Comma Match");
    }

    @Test
    void BeginningFirst_MatchesLast() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Dem");
        String[] expected = { "Moore, Demi", "Bardem, Javier"};
        assertEquals(expected, actual.toArray(), "Beginning First should precede" + 
		" Matches Last");
    }

    @Test
    void BeginningFirst_MatchesFirst() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("Ice");
        String[] expected = {"Cube, Ice", "Eve, Alice", "Straight, Beatrice", "Bejo, Bérénice"};
        assertEquals(expected, actual.toArray(), "Beginning First should precede" +
		" Matches First");
    }

    @Test
    void BeginningFirst_MatchesComma() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("rash");
        String[] expected = {"Jones, Rashida", "Kutcher, Ashton"};
        assertEquals(expected, actual.toArray(), "Beginning First should precede" + 
		" Matches Comma");
    }

    @Test
    void MatchesLast_MatchesFirst() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("oom");
        String[] expected = {"Bloom, Orlando", "Rapace, Noomi"};
        assertEquals(expected, actual.toArray(), "Matches Last should precede" + 
		" Matches First");
    }

    @Test
    void MatchesLast_MatchesComma() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("aca");
        String[] expected = {"Bacall, Lauren", "Culkin, Macaulay", "Miranda, Carmen"};
        assertEquals(expected, actual.toArray(), "Matches Last should precede" + 
		" Matches Comma");
    }

    @Test
    void BasicsCumTest() {
	autocompleter = new Autocompleter("actors.txt");
        List<String> actual = autocompleter.getCompletions("ent");
        String[] expected = {"Entertainer, Cedric the", "Osment, Haley Joel", "Potente, Franka", "Fiorentino, Linda",
	 "Laurent, Mélanie", "Broadbent, Jim", "Cassel, Vincent", "Gallo, Vincent", "Allen, Tim"};
        assertEquals(expected, actual.toArray(), "Go pass all other basic tests and try again...");
    }
}
