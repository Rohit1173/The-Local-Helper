package com.example.thelocalhelper.Api

import com.example.thelocalhelper.Data.mypost
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://nominatim.openstreetmap.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface postcodeAPI {
    @GET("reverse?format=json&zoom=18&addressdetails=1")
    suspend fun getcode(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
    ): mypost
}
object mypostApi {
    val retrofitService: postcodeAPI by lazy {
        retrofit.create(postcodeAPI::class.java)
    }
}