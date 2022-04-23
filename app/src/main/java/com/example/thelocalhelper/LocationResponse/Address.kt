package com.example.thelocalhelper.LocationResponse

import com.squareup.moshi.Json

data class Address(
    @Json(name="ISO3166-2-lvl4")
    val ISO3166_2_lvl4: String,
    val country: String,
    val country_code: String,
    val house_number: String,
    val municipality: String,
    val postcode: String,
    val road: String,
    val state: String,
    val village: String
)