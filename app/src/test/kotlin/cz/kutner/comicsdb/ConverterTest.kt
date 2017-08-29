package cz.kutner.comicsdb

import java.io.File


class ConverterTest {
  /*  @Test
    fun SeriesDetailTest() {
        try {
            val batman = SeriesParser().parseSeriesDetail(readFile("/serie_batman.html").byteInputStream(), "utf-8")
            assertEquals("Batman - NEW 52", batman.name)
            assertEquals(8, batman.comicses.count())
            assertEquals(355, batman.id)
            assertEquals(batman.comicses.count(), batman.numberOfComicses)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun AuthorDetailTest() {
        try {
            val miller = AuthorParser().parseAuthorDetail(readFile("/author_miller.html").byteInputStream(), "utf-8")
            assertEquals("Miller, Frank", miller.name)
            assertEquals("USA", miller.country)
            assertEquals(335, miller.id)
            assertEquals(44, miller.comicses.count())
            assertEquals("http://comicsdb.cz/picautor/xid335.jpg.pagespeed.ic.52Q4ZzHlgf.jpg", miller.photoUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Test
    fun ComicsDetailTest() {
        try {
            val comics_300 = ComicsParser().parseComicsDetail(readFile("/comics_300.html").byteInputStream(), "utf-8")
            assertEquals("300", comics_300.name)
            assertEquals("pevná", comics_300.binding)
            assertEquals("325 x 245 mm", comics_300.format)
            assertEquals("barevný", comics_300.print)
            assertEquals("999 Kč", comics_300.price)
            assertEquals("historický, dobrodružný, drama", comics_300.genre)
            assertEquals(94, comics_300.rating)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
*/
    private fun readFile(fileName: String): String {
        val url = this::class.java.getResource(fileName)
        return File(url.file).readText(charset("windows-1250"))
    }
}
