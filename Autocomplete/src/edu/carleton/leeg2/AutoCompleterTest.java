package edu.carleton.leeg2;

import static org.junit.jupiter.api.Assertions.*;

class AutoCompleterTest {

    private AutoCompleter autoCompleter;

    /**
     * Sets up the test fixture
     * Called before every test case method
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        autoCompleter = new AutoCompleter("actors.txt");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getCompletions() {
        fail("not yet implemented");
    }

    //Checks that file is imported into a list
    @org.junit.jupiter.api.Test
    void importList() {
        assertEquals(Arrays.asList());
    }

    //Throws exception when file is not found
    @org.junit.jupiter.api.Test
    void checkFile() {

    }
}