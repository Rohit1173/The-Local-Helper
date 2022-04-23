package com.example.thelocalhelper

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.thelocalhelper.Api.GeoApiService
import com.example.thelocalhelper.Api.GeoRetrofitInstance
import com.example.thelocalhelper.LocationResponse.GeoLocationResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class GroupChatViewModel(application: Application): AndroidViewModel(application) {

    var lat : String ="lat"
    var lon : String ="lon"
    private var context:Context = getApplication<Application>().applicationContext
    lateinit var location: FusedLocationProviderClient
    val locationLiveData = MutableLiveData<GeoLocationResponse?>()
//    var geoLocationLiveData: MutableLiveData<GeoLocationResponse?> = locationLiveData
    private suspend fun getLocationDetails(): GeoLocationResponse? {
        getMyLocation(context)
        val request = GeoRetrofitInstance.geoApiClient.getChatRoom("json",lat,lon,20.toString())
        if(request.isSuccessful) {
            var data = request.body()!!
        }
        return null
    }

    fun getLocatin(){
        viewModelScope.launch{
            val response = getLocationDetails()
            locationLiveData.postValue(response)
        }
    }




    fun getMyLocation(context: Context) {
        location = LocationServices.getFusedLocationProviderClient(context)
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
            if(it!=null){
                lat=it.latitude.toString()
                lon=it.longitude.toString()
            }
        }
    }
}