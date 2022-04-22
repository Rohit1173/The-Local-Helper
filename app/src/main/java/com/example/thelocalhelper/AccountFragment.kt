package com.example.thelocalhelper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class AccountFragment : Fragment() {

    lateinit var long_lat: TextView
    lateinit var location: FusedLocationProviderClient
    lateinit var my_longitude: String
    lateinit var my_latitude: String
    lateinit var status: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_account, container, false)
        status = v.findViewById(R.id.status)
        val network = NetworkConnection(requireContext())
        requireActivity().runOnUiThread {
            network.observe(viewLifecycleOwner) { isConnected ->
                if (isConnected) {
                    status.text = "ONLINE"

                } else {
                    status.text = "OFFLINE"
                }
            }
        }
        val u_name: TextView = v.findViewById(R.id.u_name)

        long_lat = v.findViewById(R.id.long_lat)
        getMyLocation(requireContext())
        u_name.text = requireActivity().getIntent().getExtras()!!.getString("username")
        return v

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
            if (it != null) {
                //Toast.makeText(context,"${it.latitude},${it.longitude}", Toast.LENGTH_LONG).show()
                my_latitude = it.latitude.toString()
                my_longitude = it.longitude.toString()
                long_lat.text = my_latitude + " , " + my_longitude
            }

        }
    }
}