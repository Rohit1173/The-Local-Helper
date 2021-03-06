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
import com.example.thelocalhelper.Data.LoginData
import com.example.thelocalhelper.LoginViewModel
import com.example.thelocalhelper.R
import com.example.thelocalhelper.postcodeviewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject


class LoginFragment : Fragment() {

    lateinit var log_btn: Button
    lateinit var logtxt: TextView
    lateinit var log_user: TextInputEditText
    lateinit var log_password: TextInputEditText
    lateinit var lay_log_user: TextInputLayout
    lateinit var lay_log_password: TextInputLayout
    lateinit var vm: LoginViewModel
    lateinit var log_msg: String
    lateinit var log_er_msg: String
    lateinit var my_latitude:String
    lateinit var my_longitude:String
    lateinit var post:String
    lateinit var post_vm:postcodeviewmodel
    lateinit var location:FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        getMyLocation(requireContext())
        post_vm = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(postcodeviewmodel::class.java)
        post_vm.myname.observe(viewLifecycleOwner){
            post= it.address.postcode
        }
        log_btn = v.findViewById(R.id.log_btn)
        logtxt = v.findViewById(R.id.change_to_signup)
        log_user = v.findViewById(R.id.log_user)
        log_password = v.findViewById(R.id.log_password)
        lay_log_user = v.findViewById(R.id.lay_log_user)
        lay_log_password = v.findViewById(R.id.lay_log_password)
        vm = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(LoginViewModel::class.java)
        vm.log_Response.observe(
            viewLifecycleOwner
        ) {
            if (it.isSuccessful) {
                try {
                    val jsonObject = JSONObject(Gson().toJson(it.body()))
                    log_msg = jsonObject.getString("message")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                Toast.makeText(context, log_msg, Toast.LENGTH_LONG).show()
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("username", log_user.text.toString().trim())
                intent.putExtra("longitude",my_longitude)
                intent.putExtra("latitude",my_latitude)
                intent.putExtra("post",post)
                startActivity(intent)
            } else {
                try {
                    val jObjError = JSONObject(it.errorBody()!!.string())
                    log_er_msg = jObjError.getString("message")
                    if (log_er_msg.contains("not")) {
                        lay_log_user.error = "Invalid Credentials"
                        lay_log_password.error = "Invalid Credentials"
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        log_user.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty()) {
                lay_log_user.error = null
            }
        }
        log_password.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty()) {
                lay_log_password.error = null
            }
        }
        logtxt.setOnClickListener {
            findNavController().navigate(R.id.action_fr_login_to_fr_signup)
        }
        log_btn.setOnClickListener {
            if (log_user.text.toString().trim().isEmpty()) {
                lay_log_user.error = "This Field Cannot Be Empty"
            } else {
                lay_log_user.error = null
            }
            if (log_password.text.toString().trim().isEmpty()) {
                lay_log_password.error = "Password Cannot Be Empty"
            } else {
                lay_log_password.error = null
            }
            if (log_user.text.toString().trim()
                    .isNotEmpty() && log_password.text.toString().trim().isNotEmpty()
            ) {
                vm.checkLogin(
                    LoginData(
                        log_user.text.toString().trim(),
                        log_password.text.toString().trim()
                    )
                )
            } else {
                Toast.makeText(requireContext(), "PLEASE FILL ALL THE DETAILS", Toast.LENGTH_LONG)
                    .show()
            }
        }
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

            }

        }
    }
}
