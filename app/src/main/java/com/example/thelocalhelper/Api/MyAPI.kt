package com.example.thelocalhelper.Api

import com.example.thelocalhelper.Data.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyAPI {
    @FormUrlEncoded
    @POST("user/create")
    fun createuser(
        @Field("userName") userName: String,
        @Field("userPassword") userPassword: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun loginuser(
        @Field("usernameOrEmail") userName: String,
        @Field("password") userPassword: String,
    ): Call<DefaultResponse>
}
