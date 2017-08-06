package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Classified
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClassifiedService {
    @GET("/api.php")
    fun filteredClassifiedList(@Query("str") page: Int, @Query("id") categoryId: Int): Call<List<Classified>>
}
