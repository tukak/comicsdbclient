package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Classified
import retrofit.http.GET
import retrofit.http.Query

interface ClassifiedService {
    @GET("/bazar.php")
    fun filteredClassifiedList(@Query("str") page: Int, @Query("id") categoryId: Int, @Query("val") searchText: String): List<Classified>
}
