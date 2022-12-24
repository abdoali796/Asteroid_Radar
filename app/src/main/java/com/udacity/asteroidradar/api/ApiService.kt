package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*


private const val key="*******************"

private const val BASE_URL ="https://api.nasa.gov/"
//to get start date
 fun getToday():String{
    val time=Calendar.getInstance().time
    val format=SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
    val today=format.format(time)
    return today
}



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).
           addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()




interface ApiService {

////one days
//    @GET("neo/rest/v1/feed")
//    suspend fun getPropertiesToday(
//        @Query("api_key") apiKey: String = key,
//        @Query("start_date") startDate: String= getToday(),
//        @Query("end_date") endDate: String= getToday()
//
//    ):String
//7days
    @GET("neo/rest/v1/feed")
    suspend fun getProperties(
        @Query("api_key") apiKey: String = key,
        @Query("start_date") startDate: String= getToday(),

    ):String

//  pictureOfDay
    @GET("planetary/apod")
    suspend fun getPictuer(
    @Query("api_key") apiKey: String = key
    ):PictureOfDay

}
object AsterApi{
    val retrofitService :ApiService by lazy { retrofit.create(ApiService::class.java) }
}