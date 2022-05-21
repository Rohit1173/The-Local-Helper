package com.example.thelocalhelper.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.thelocalhelper.Activities.MapsActivity
import com.example.thelocalhelper.NetworkConnection
import com.example.thelocalhelper.R


class AccountFragment : Fragment() {

    lateinit var longitude: TextView
    lateinit var latitude: TextView
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

        longitude = v.findViewById(R.id.longitude)
        latitude = v.findViewById(R.id.latitude)
        u_name.text = requireActivity().getIntent().getExtras()!!.getString("username")
        longitude.text=requireActivity().getIntent().getExtras()!!.getString("longitude")
        latitude.text=requireActivity().getIntent().getExtras()!!.getString("latitude")

        val viewOnMapButton: Button = v.findViewById(R.id.mapactivitybutton)
        viewOnMapButton.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            intent.putExtra("longitude",longitude.text.toString())
            intent.putExtra("latitude",latitude.text.toString())
            startActivity(intent);
        }

        return v

    }



}
