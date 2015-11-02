package cz.kutner.comicsdb

import cz.kutner.comicsdb.connector.converter.AuthorDetailConverter
import cz.kutner.comicsdb.connector.parser.ComicsParser
import cz.kutner.comicsdb.model.Author
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit.converter.ConversionException
import retrofit.mime.TypedString
import java.io.File


class ConverterTest {
    @Test
    fun AuthorDetailTest() {
        val typedInput = TypedString(readFile("/author_miller.html"))
        try {
            val miller = AuthorDetailConverter().fromBody(typedInput, Author::class.java) as Author
            assertEquals("Miller, Frank", miller.name)
            assertEquals("USA", miller.country)
            assertEquals(335, miller.id)
            assertEquals(44, miller.comicses.count())
        } catch (e: ConversionException) {
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
        } catch (e: ConversionException) {
            e.printStackTrace()
        }

    }

    fun readFile(fileName: String): String {
        val url = this.javaClass.getResource(fileName)
        return File(url.file).readText("windows-1250")
    }
}
