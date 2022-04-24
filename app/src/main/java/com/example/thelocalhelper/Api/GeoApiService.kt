package com.example.thelocalhelper.Api

import com.example.thelocalhelper.LocationResponse.GeoLocationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApiService {

    @GET("/reverse?")
    fun getChatRoom(
        @Query("format") format: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("zoom") zoom: String
    ): Response<GeoLocationResponse>
}