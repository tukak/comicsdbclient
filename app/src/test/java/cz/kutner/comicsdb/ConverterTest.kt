package cz.kutner.comicsdb

import org.junit.Test

import java.io.File

import cz.kutner.comicsdb.connector.converter.AuthorDetailConverter
import cz.kutner.comicsdb.connector.converter.ComicsConverter
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.model.Comics
import retrofit.converter.ConversionException
import retrofit.mime.TypedString

import org.junit.Assert.assertEquals


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
        val typedInput = TypedString(readFile("/comics_300.html"))
        try {
            val comics_300 = ComicsConverter().fromBody(typedInput, Comics::class.java) as Comics
            assertEquals("300", comics_300.name)
            assertEquals("325 x 245 mm", comics_300.format)
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
