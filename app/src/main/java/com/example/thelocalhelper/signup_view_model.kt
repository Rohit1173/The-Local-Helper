package com.example.thelocalhelper

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thelocalhelper.Api.retrofitinstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signup_view_model(application: Application): AndroidViewModel(application) {
    var sign_Response: MutableLiveData<Response<default_response>> = MutableLiveData()
    fun pushSignup(signupdata:signupdata) {
        retrofitinstance.api.createuser(
            signupdata.sig_username,
            signupdata.sig_password
        ).enqueue(object : Callback<default_response> {
            override fun onResponse(
                call: Call<default_response>,
                response: Response<default_response>
            ) {
                sign_Response.value = response
            }

            override fun onFailure(call: Call<default_response>, t: Throwable) {
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}