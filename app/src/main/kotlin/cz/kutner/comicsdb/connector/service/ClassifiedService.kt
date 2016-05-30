package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Classified
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClassifiedService {
    @GET("/bazar.php")
    fun filteredClassifiedList(@Query("str") page: Int, @Query("id") categoryId: Int, @Query("val") searchText: String): Call<List<Classified>>
}
