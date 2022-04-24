package com.example.thelocalhelper

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.thelocalhelper.Api.RetrofitInstance
import com.example.thelocalhelper.Data.SignUpData
import com.example.thelocalhelper.Data.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    var sign_Response: MutableLiveData<Response<DefaultResponse>> = MutableLiveData()
    fun pushSignup(signupdata: SignUpData) {
        RetrofitInstance.api.createuser(
            signupdata.sig_username,
            signupdata.sig_password,
            signupdata.lat,
            signupdata.long
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                sign_Response.value = response
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
