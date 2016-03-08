package cz.kutner.comicsdb

import cz.kutner.comicsdb.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class UtilsTest {
    @Test
    fun utils_fixUrl() {
        assertEquals("http://comicsdb.cz/ahoj", Utils.fixUrl("/ahoj"))
        assertEquals("http://comicsdb.cz/ahoj", Utils.fixUrl("http://comicsdb.cz/ahoj"))
        assertEquals("data:ahoj", Utils.fixUrl("data:ahoj"))
        assertEquals("http://comicsdb.cz/pics/5972/b_c5972-3f03c75fca3242c51524167e31e78c62.jpg", Utils.fixUrl("/pics/5972/b_c5972-3f03c75fca3242c51524167e31e78c62.jpg"))
    }
}