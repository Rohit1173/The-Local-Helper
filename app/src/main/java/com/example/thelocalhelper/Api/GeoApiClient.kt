package com.example.thelocalhelper.Api

import com.example.thelocalhelper.LocationResponse.GeoLocationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query

class GeoApiClient(
    private val geoApiService: GeoApiService
) {
    suspend fun getChatRoom(
        format: String,
        lat: String,
        lon: String,
        zoom: String
    ): Response<GeoLocationResponse> {
        return  geoApiService.getChatRoom(format,lat,lon,zoom)
    }
}