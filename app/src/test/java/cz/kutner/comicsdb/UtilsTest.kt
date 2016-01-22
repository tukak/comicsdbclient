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
        assertEquals("http://comicsdb.cz/pics/5972/b_c5972-3f03c75fca3242c51524167e31e78c62.jpg", Utils.fixUrl("/pics/5972/b_c5972-3f03c75fca3242c51524167e31e78c62.jpg", prefix))
    }
}