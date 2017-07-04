package cz.kutner.comicsdb

import cz.kutner.comicsdb.utils.fixUrl
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class UtilsTest {
    @Test
    fun utils_fixUrl() {
        assertEquals("http://comicsdb.cz/ahoj", "/ahoj".fixUrl())
        assertEquals("http://comicsdb.cz/ahoj", "http://comicsdb.cz/ahoj".fixUrl())
        assertEquals("data:ahoj", "data:ahoj".fixUrl())
        assertEquals("http://comicsdb.cz/pics/5972/b_c5972-3f03c75fca3242c51524167e31e78c62.jpg", "/pics/5972/b_c5972-3f03c75fca3242c51524167e31e78c62.jpg".fixUrl())
    }
}