package com.example.thelocalhelper


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class chat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar!!.hide()
        val bottombar:BottomNavigationView=findViewById(R.id.bottom_navigation)
        val navController = findNavController(R.id.chat_start_frag)
        bottombar.setupWithNavController(navController)
    }
}