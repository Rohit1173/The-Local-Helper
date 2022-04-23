package com.example.thelocalhelper.Api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GeoRetrofitInstance {
    private const val BASE_URL_STREET ="https://nominatim.openstreetmap.org/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL_STREET)
        .build()

    val api: my_api by lazy {
        retrofit.create(my_api::class.java)
    }
}