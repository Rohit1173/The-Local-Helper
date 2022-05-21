package com.example.thelocalhelper.Data

import com.squareup.moshi.Json

data class address(
    @Json(name="postcode") val postcode:String
)
