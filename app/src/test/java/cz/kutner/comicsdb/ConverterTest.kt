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
        val authorDetailConverter = AuthorDetailConverter()
        val typedInput = TypedString(readFile("/author_miller.html"))
        try {
            val miller = authorDetailConverter.fromBody(typedInput, Author::class.java) as Author
            assertEquals("Miller, Frank", miller.name)
        } catch (e: ConversionException) {
            e.printStackTrace()
        }

    }

    @Test
    fun ComicsDetailTest() {
        val comicsConverter = ComicsConverter()
        val typedInput = TypedString(readFile("/comics_300.html"))
        try {
            val comics_300 = comicsConverter.fromBody(typedInput, Comics::class.java) as Comics
            assertEquals("300", comics_300.name)
        } catch (e: ConversionException) {
            e.printStackTrace()
        }

    }

    fun readFile(fileName: String): String {
        val url = this.javaClass.getResource(fileName)
        return File(url.file).readText("windows-1250")
    }
}
