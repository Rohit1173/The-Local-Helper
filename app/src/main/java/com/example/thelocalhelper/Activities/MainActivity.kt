package com.example.thelocalhelper.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thelocalhelper.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
    }
}
