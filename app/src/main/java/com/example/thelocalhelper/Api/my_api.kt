package com.example.thelocalhelper.Api

import com.example.thelocalhelper.default_response
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface my_api {
    @FormUrlEncoded
    @POST("user/create")
    fun createuser(
        @Field("userName") userName: String,
        @Field("userPassword") userPassword: String,
    ): Call<default_response>

    @FormUrlEncoded
    @POST("user/login")
    fun loginuser(
        @Field("usernameOrEmail") userName: String,
        @Field("password") userPassword: String,
    ): Call<default_response>
}