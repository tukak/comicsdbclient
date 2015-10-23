package cz.kutner.comicsdb

import org.junit.Test

import org.junit.Assert.*

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class UtilsTest {
    @Test
    fun utils_fixUrl() {
        assertEquals(Utils.fixUrl("ahoj"), "http://comicsdb.cz/ahoj")
        assertEquals(Utils.fixUrl("http://comicsdb.cz/ahoj"), "http://comicsdb.cz/ahoj")
        assertEquals(Utils.fixUrl("data:ahoj"), "data:ahoj")
    }
}