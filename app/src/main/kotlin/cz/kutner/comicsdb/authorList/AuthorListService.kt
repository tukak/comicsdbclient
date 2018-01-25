package cz.kutner.comicsdb.authorList

import cz.kutner.comicsdb.model.Author
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthorListService {
    @GET("/api.php?get=autor_list")
    fun listAuthors(@Query("start") start: Int, @Query("records") records: Int, @Query("search") keyword: String): Deferred<List<Author>>
}
