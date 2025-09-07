package org.unizd.rma.curko.model



import org.unizd.rma.curko.model.ApodItem
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    // https://api.nasa.gov/planetary/apod?api_key=...&start_date=YYYY-MM-DD&end_date=YYYY-MM-DD&thumbs=true
    @GET("planetary/apod")
    suspend fun getApodRange(
        @Query("api_key") apiKey: String,
        @Query("start_date") start: String,
        @Query("end_date") end: String,
        @Query("thumbs") thumbs: Boolean = true
    ): List<ApodItem>
}
