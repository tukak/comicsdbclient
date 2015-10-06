package cz.kutner.comicsdb;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UtilsTest {
    @Test
    public void utils_fixUrl() {
        assertEquals(Utils.INSTANCE$.fixUrl("ahoj"), "http://comicsdb.cz/ahoj");
        assertEquals(Utils.INSTANCE$.fixUrl("http://comicsdb.cz/ahoj"), "http://comicsdb.cz/ahoj");
        assertEquals(Utils.INSTANCE$.fixUrl("data:ahoj"), "data:ahoj");
    }
}