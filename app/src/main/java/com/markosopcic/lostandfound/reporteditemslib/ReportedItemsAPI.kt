package com.markosopcic.lostandfound.reporteditemslib

import com.markosopcic.lostandfound.reporteditemslib.models.APIReportedItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ReportedItemsAPI {

    @GET("/api/search")
    fun getReportedItems(@Query("longitude") longitude: Double, @Query("latitude") latitude: Double, @Query("range") range: Double): Single<List<APIReportedItem>>
}
