package com.example.thelocalhelper

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkViewModel:ViewModel() {
    private val _isRealtime = MutableLiveData<Boolean>()
    val isRealtime: LiveData<Boolean> = _isRealtime
    fun check_online_status(context: Context,lifecycleOwner: LifecycleOwner) {
        val networkConnection = NetworkConnection(context)
        viewModelScope.launch(Dispatchers.IO) {
            networkConnection.observe(lifecycleOwner) {
                _isRealtime.value =it
            }
        }

    }
}