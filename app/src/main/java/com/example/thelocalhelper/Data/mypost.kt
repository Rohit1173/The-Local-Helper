package com.example.thelocalhelper.Data

import com.squareup.moshi.Json

data class mypost(
    @Json(name="address") val address:address
)
