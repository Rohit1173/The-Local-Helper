package com.example.thelocalhelper

import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.example.thelocalhelper.Api.RetrofitInstance.api
import com.example.thelocalhelper.Api.mypostApi
import com.example.thelocalhelper.Api.mypostApi.retrofitService
import com.example.thelocalhelper.Data.mypost
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class postcodeviewmodel(application: Application):AndroidViewModel(application) {
    lateinit var location: FusedLocationProviderClient
    private val context = getApplication<Application>().applicationContext
    private var my_Longitude: String? = null
    private var my_latitude: String? = null
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _myweather = MutableLiveData<mypost>()
    val myname: LiveData<mypost> = _myweather
    init{
        location = LocationServices.getFusedLocationProviderClient(context)
        getweather()
        getMyLocation()
    }
    fun getweather() {

        viewModelScope.launch {
            try {
                _myweather.value = retrofitService.getcode(my_latitude!!,my_Longitude!!)
                        //"23.1234","12.123456")
                _status.value = "SUCCESS"


            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    fun getMyLocation() {
        val task = location.lastLocation
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                my_latitude = it.latitude.toString()
                my_Longitude = it.longitude.toString()
                viewModelScope.launch {
                    try {
                        _myweather.value = retrofitService.getcode(my_latitude!!, my_Longitude!!)
                        _status.value = "SUCCESS"


                    } catch (e: Exception) {
                        _status.value = "Failure: ${e.message}"
                    }
                }

            }

        }
    }

}