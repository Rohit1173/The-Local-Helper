package com.example.thelocalhelper

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SignUpFragment : Fragment() {

    lateinit var signup_btn: Button
    lateinit var sigtxt:TextView
    lateinit var set_uname:TextInputEditText
    lateinit var set_password:TextInputEditText
    lateinit var lay_set_uname:TextInputLayout
    lateinit var lay_set_password:TextInputLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_signup, container, false)
         signup_btn = v.findViewById(R.id.f_sign_up_btn)
         sigtxt=v.findViewById(R.id.change_to_login)
        set_uname =v.findViewById(R.id.set_uname)
         set_password=v.findViewById(R.id.set_password)
         lay_set_uname=v.findViewById(R.id.lay_set_uname)
         lay_set_password=v.findViewById(R.id.lay_set_password)
        sigtxt.setOnClickListener {
            findNavController().navigate(R.id.action_fr_signup_to_fr_login)
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
            if(checks()) {
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("username", set_uname.text.toString().trim())
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(),"PLEASE FILL ALL THE DETAILS",Toast.LENGTH_LONG).show()
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


}