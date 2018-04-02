package edu.carleton.leeg2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutocompleterTest {


    private Autocompleter autocompleter;

    @BeforeEach
    void setUp() {
        autocompleter = new Autocompleter("edgeactors.txt");
    }

    @AfterEach
    void tearDown() {
        autocompleter = null;
    }

    //Edge Cases
    @Test
    void testEmptyStringInput() {
        List<String> actual  = autocompleter.getCompletions("");
        String[] expected = {};
        assertEquals(expected, actual.toArray(), "Empty string input does not " +
                "return an empty list");
    }

    @Test
    void testNoMatchString() {
        List<String> actual  = autocompleter.getCompletions("Bacone");
        String[] expected = {};
        assertEquals(expected, actual.toArray(), "String input with no matches " +
                "does not return an empty list");
    }

    @Test
    void testStringWithSpaces() {

        List<String> actual  = autocompleter.getCompletions("F a ri s");
        String[] expected = {"Faris, Anna"};
        assertEquals(expected, actual.toArray(), "String input with spaces" +
                "does not return trimmed name.");

    }
    @Test
    void testNullStringInput() {
        List<String> actual = autocompleter.getCompletions(null);
        String[] expected = {};
        assertEquals(expected, actual.toArray(), "Null string input" +
                "does not return an empty list.");

    }
    @Test
    void testWeirdCharacterInput() {
        List<String> actual = autocompleter.getCompletions("Beyonce");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with non-ASCII" +
                "(accented) characters not found when ASCII version used.");

    }
    @Test
    void testNameApostropheInput() {
        List<String> actual = autocompleter.getCompletions("OBrien");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with apostrophe" +
                "characters not found when apostrophe omitted in search");
    }

    @Test
    void testNameApostropheInput2() {
        List<String> actual = autocompleter.getCompletions("O'Brien");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with apostrophe" +
                "characters not found when search string includes apostrophe.");
    }
    @Test
    void testNameHyphenInput() {
        List<String> actual = autocompleter.getCompletions("JeanPaul");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with hyphen" +
                "characters not found when hyphen omitted in search");
    }

    @Test
    void testNameHyphenInput2() {
        List<String> actual = autocompleter.getCompletions("Jean-Paul");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with hyphen" +
                "characters not found when search string includes hyphen");
    }

    @Test
    void testNamePeriodInput() {
        List<String> actual = autocompleter.getCompletions("FMurray");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with period" +
                "characters not found when period omitted in search");
    }

    @Test
    void testNamePeriodInput2() {
        List<String> actual = autocompleter.getCompletions("F.Murray");
        String[] empty = {};
        assertNotEquals(empty, actual.toArray(), "Names with period" +
                "characters not found when search string includes period");
    }

    @Test
    void testSingleName() {
        List<String> actual = autocompleter.getCompletions("Cher");
        String[] expected = {"Cher", "Fletcher, Laura"};
        assertEquals(expected, actual.toArray(), "Error when sorting" +
                "single names.");

    }

    @Test
    void testSingleName2() {
        List<String> actual = autocompleter.getCompletions("her");
        String[] expected = {"Cher", "Fletcher, Laura"};
        assertEquals(expected, actual.toArray(), "Single names" +
                "not treated as last names");

    }
    @Test
    void testLowercaseInput() {
        List<String> actual = autocompleter.getCompletions("faris");
        String[] expected = {"Faris, Anna"};
        assertEquals(expected, actual.toArray(), "If input is lowercase" +
                "matches are not successfully found.")
    }

    @Test
    void testRandomCaps() {
        List<String> actual = autocompleter.getCompletions("fArIS");
        String[] expected = {"Faris, Anna"};
        assertEquals(expected, actual.toArray(), "If input has nonsensical" +
                "casing matches are not successfully found.");

    }

    @Test
    void testMultipleMatch() {
        List<String> actual = autocompleter.getCompletions("Ei");
        String[] expected = {"Simein, Eileen", "Mein, Hannah"};
        assertEquals(expected, actual.toArray(), "Incorrectly sorted" +
                "when substring matched beginning of first and middle of last.");

    }

    @Test
    void testMultipleMatch2() {
        List<String> actual = autocompleter.getCompletions("omar");
        String[] expected = {"Omario, Mario", "James, Omar"};
        assertEquals(expected, actual.toArray(), "Incorrectly sorted" +
                "when substring matched beginning of last name and across " +
                "comma.");
    }


    @Test
    void testSameCategory() {
        List<String> actual = autocompleter.getCompletions("es");
        String[] expected = {"Tress, Scott", "James, John"};
        assertEquals(expected, actual.toArray(), "Incorrectly sorted" +
                "names in the same category based on index.");
    }


    //Basic Cases
}