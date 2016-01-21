package cz.kutner.comicsdb

import org.junit.Test

import org.junit.Assert.*

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class UtilsTest {
    @Test
    fun utils_fixUrl() {
        val prefix = "http://comicsdb.cz"
        assertEquals("http://comicsdb.cz/ahoj", Utils.fixUrl("/ahoj", prefix))
        assertEquals("http://comicsdb.cz/ahoj", Utils.fixUrl("http://comicsdb.cz/ahoj", prefix))
        assertEquals("data:ahoj", Utils.fixUrl("data:ahoj", prefix))
    }
}