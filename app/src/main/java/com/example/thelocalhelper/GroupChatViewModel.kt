package com.example.thelocalhelper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thelocalhelper.LocationResponse.GeoLocationResponse

class GroupChatViewModel: ViewModel() {
    lateinit var address:MutableLiveData<GeoLocationResponse>
    init {
        address=MutableLiveData()
    }

    init {

    }

    fun getAddress(){

    }
}