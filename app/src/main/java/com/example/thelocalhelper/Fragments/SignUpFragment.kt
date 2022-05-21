package com.example.thelocalhelper.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thelocalhelper.Activities.ChatActivity
import com.example.thelocalhelper.Data.SignUpData
import com.example.thelocalhelper.R
import com.example.thelocalhelper.SignUpViewModel
import com.example.thelocalhelper.postcodeviewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject


class SignUpFragment : Fragment() {

    lateinit var signup_btn: Button
    lateinit var sigtxt: TextView
    lateinit var set_uname: TextInputEditText
    lateinit var set_password: TextInputEditText
    lateinit var lay_set_uname: TextInputLayout
    lateinit var lay_set_password: TextInputLayout
    private lateinit var viewModel: SignUpViewModel
    lateinit var s_msg: String
    lateinit var f_msg: String
     lateinit var my_latitude:String
     lateinit var my_longitude:String
    lateinit var post:String
    lateinit var location: FusedLocationProviderClient
    lateinit var vm:postcodeviewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_signup, container, false)
        getMyLocation(requireContext())
        vm = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(postcodeviewmodel::class.java)
        vm.myname.observe(viewLifecycleOwner){
            post= it.address.postcode
        }

        signup_btn = v.findViewById(R.id.f_sign_up_btn)
        sigtxt = v.findViewById(R.id.change_to_login)
        set_uname = v.findViewById(R.id.set_uname)
        set_password = v.findViewById(R.id.set_password)
        lay_set_uname = v.findViewById(R.id.lay_set_uname)
        lay_set_password = v.findViewById(R.id.lay_set_password)
        sigtxt.setOnClickListener {
            findNavController().navigate(R.id.action_fr_signup_to_fr_login)
        }
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(SignUpViewModel::class.java)
        viewModel.sign_Response.observe(
            viewLifecycleOwner
        ) {

            if (it.isSuccessful) {
                try {
                    val jsonObject = JSONObject(Gson().toJson(it.body()))
                    s_msg = jsonObject.getString("message")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                Toast.makeText(context, s_msg, Toast.LENGTH_LONG).show()
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("username", set_uname.text.toString().trim())
                intent.putExtra("longitude",my_longitude)
                intent.putExtra("latitude",my_latitude)
                intent.putExtra("post",post)
                startActivity(intent)
            } else {
                try {
                    val jObjError = JSONObject(it.errorBody()!!.string())
                    f_msg = jObjError.getString("message")
                    if (f_msg.contains("username")) {
                        lay_set_uname.error = "Username is already taken"
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        set_uname.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty()) {
                lay_set_uname.error = null
            }
        }
        set_password.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty()) {
                lay_set_password.error = null
            }
        }
        signup_btn.setOnClickListener {
            if (set_uname.text.toString().trim().isEmpty()) {
                lay_set_uname.error = "Username cannot be empty"
            }
            if (set_password.text.toString().trim().isEmpty()) {
                lay_set_password.error = "Password cannot be empty"
            }
            if (checks()) {
                viewModel.pushSignup(
                    SignUpData(
                        set_uname.text.toString().trim(),
                        set_password.text.toString().trim(),
                        my_latitude,
                        my_longitude
                    )
                )
            } else {
                Toast.makeText(requireContext(), "PLEASE FILL ALL THE DETAILS", Toast.LENGTH_LONG)
                    .show()
            }
        }
        return v
    }

    private fun checks(): Boolean {
        if (set_uname.text.toString().trim().isNotEmpty() &&
            set_password.text.toString().trim().isNotEmpty()
        ) {
            return true
        }
        return false
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

            }

        }
    }
}
