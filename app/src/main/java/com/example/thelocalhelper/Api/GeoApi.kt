package com.example.thelocalhelper.Api

import com.example.thelocalhelper.default_response
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {

    @FormUrlEncoded
    @GET("/reverse")
    fun getRoomChat(
        @Query("format") format: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("zoom") zoom: String
    ): Call<default_response>
}