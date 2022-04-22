package com.example.thelocalhelper

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class EntranceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_entrance, container, false)
        val login:Button = v.findViewById(R.id.login)
        val signup:Button =v.findViewById(R.id.signup)
        login.setOnClickListener {
            findNavController().navigate(R.id.action_fr_entrance_to_fr_login)
        }
        signup.setOnClickListener {
            findNavController().navigate(R.id.action_fr_entrance_to_fr_signup)
        }
        return v
    }


}