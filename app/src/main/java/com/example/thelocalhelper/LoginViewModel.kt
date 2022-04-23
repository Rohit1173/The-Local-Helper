package com.example.thelocalhelper

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.thelocalhelper.Api.RetrofitInstance
import com.example.thelocalhelper.Data.LoginData
import com.example.thelocalhelper.Data.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var log_Response: MutableLiveData<Response<DefaultResponse>> = MutableLiveData()
    fun checkLogin(loginData: LoginData) {
        RetrofitInstance.api.loginuser(
            loginData.log_username,
            loginData.log_password
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                log_Response.value = response
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
