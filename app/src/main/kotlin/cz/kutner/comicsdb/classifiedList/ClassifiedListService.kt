package cz.kutner.comicsdb.classifiedList

import cz.kutner.comicsdb.model.Classified
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClassifiedListService {
    @GET("/api.php?get=bazar")
    fun filteredClassifiedList(@Query("start") start: Int, @Query("records") records: Int, @Query("id") categoryId: Int): Deferred<List<Classified>>
}
